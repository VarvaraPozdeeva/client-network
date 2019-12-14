package com.unn.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.unn.Status;
import com.unn.MyStompSessionHandler;
import lombok.Data;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import sample.ClientMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class Service {
    private WebSocketStompClient socketService;
    private StompSession session;
    private String userId;
    WebResource restService;
    ObjectMapper mapper;
    List<String> lockingElements;
    Client client;
    NetworkModel model;

    public Service(NetworkModel model) {
        this.model = model;
        lockingElements = new ArrayList<>();
        createSocket();
        ClientConfig config = new DefaultClientConfig();
        mapper = new ObjectMapper();
        client = Client.create(config);
        restService = client.resource(UriBuilder.fromUri("http://localhost:8080/data/").build());
    }

    public void createSocket() {
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));

        SockJsClient sockJsClient = new SockJsClient(transports);
        socketService = new WebSocketStompClient(sockJsClient);
        socketService.setMessageConverter(new MappingJackson2MessageConverter());

        String url = "ws://localhost:8080/network";
        userId = "spring-" + ThreadLocalRandom.current().nextInt(1, 99);
        StompSessionHandler sessionHandler = new MyStompSessionHandler(userId, this);
        try {
            session = socketService.connect(url, sessionHandler).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Status status){
        session.send("/app/hello", new ClientMessage(userId, status.getValue(), "",""));
    }
    public void sendMessage(Status status, String idNe){
        session.send("/app/hello", new ClientMessage(userId, status.getValue(), idNe,""));
    }
    public void sendMessage(Status status, String idNeA, String idNeZ){
        session.send("/app/hello", new ClientMessage(userId, status.getValue(), idNeA,idNeZ));
    }

    public List<NetworkElement> getNetworkElements() {
        String neString = restService.path("network-elements").get(String.class);
        System.out.println(neString);
        List<NetworkElement> networkElements = null;
        try {
            networkElements = mapper
                    .readValue(neString, new TypeReference<List<NetworkElement>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  networkElements == null ? new ArrayList<NetworkElement>() : networkElements;
    }


    public List<Link> getLinks() {
        String linkString = restService.path("links").get(String.class);
        System.out.println(linkString);
        List<Link> links = null;
        try {
            links = mapper
                    .readValue(linkString, new TypeReference<List<Link>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  links == null ? new ArrayList<Link>() : links;
    }

    public NetworkElement addNetworkElement(String neString) {
        ClientResponse response = restService.path("network-elements")
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, neString);
        return getEntity(response, NetworkElement.class);
    }

    public Interface addInterface(String inter, String idNe) {
        ClientResponse response = restService.path("interfaces").path(idNe)
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, inter);
        return getEntity(response, Interface.class);
    }

    public Link addLink(String link) {
        ClientResponse response = restService.path("links")
                .type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, link);
        return (Link) getEntity(response, List.class);
    }

    public NetworkElement deleteNetworkElement(String idNe) {
        ClientResponse response = restService.path("network-elements").path(idNe)
                .delete(ClientResponse.class);
        return getEntity(response, NetworkElement.class);
    }

    public Link deleteLink(String idLink) {
        ClientResponse response = restService.path("links").path(idLink)
                .delete(ClientResponse.class);
        return (Link) getEntity(response, List.class);
    }

    private <T> T getEntity(ClientResponse response, Class<T> c) {
        T entity = null;
        try {
            entity = mapper.readValue(response.getEntity(String.class), c);
            System.out.println("Response " + entity.toString());
        } catch (JsonProcessingException e) {
            System.out.println("error" + e);
            e.printStackTrace();
        }
        return entity;
    }

    public List<Link> getLinks(String id) {

        String response = restService.path("links").path(id).get(String.class);
        List<Link> links = null;

        try {
            links = mapper.readValue(response, new TypeReference<List<Link>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return  links;
    }
}
