package com.unn.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.unn.Blocking;
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
    Boolean isBlock;
    Client client;

    public Service() {
        isBlock = false;
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


    public Boolean isNotBlocking(){
        return !isBlock;
    }
    public void sendMessage(Blocking status){
        session.send("/app/hello", new ClientMessage(userId, status.getValue()));
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
        NetworkElement ne = null;
        try {
            ne = mapper.readValue(response.getEntity(String.class), NetworkElement.class);
            System.out.println("Response " + ne.toString());

        } catch (JsonProcessingException e) {
            System.out.println("error" + e);
            e.printStackTrace();
        }
        return  ne;
    }
    // Добавить интерфейсы (id ne; json string interface)
    // Добавить link (id interface a, id interface z)
    // Получить интерфесы по номеру network-element (id network-element)
    // Получить link по номеру network-element (id network-element)
    // Удаление network-element по id
    // Удаление интерфейса по id
    // Удаление link по id
}
