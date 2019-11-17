package com.unn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.unn.model.NetworkElement;
import com.unn.model.NetworkModel;
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
import sample.ServerMessage;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class Utils {

    private WebSocketStompClient stompClient;
    private StompSession session;
    private String userId;
    private ServerMessage m;
    WebResource restService;
    ObjectMapper mapper = new ObjectMapper();
    Boolean isBlock;

    public Utils() {
        isBlock = false;
        m = new ServerMessage();

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        restService = client.resource(UriBuilder.fromUri("http://localhost:8080/data/").build());


       String str =  "[" +
               "        {\"network-element\":" +
                "            {" +
                "                \"name\" : \"nn-gagar-gw1\"," +
                "                    \"type\" : \"router\"" +
                "            }," +
                "            \"interface\":" +
                "                           [" +
                "                               {" +
                "                                   \"name\" : \"FastEthernet1\"," +
                "                                      \"mac-address\" : \"00:60:70:75:4e:02\"" +
                "                                   }," +
                "                               {" +
                "                                   \"name\" : \"FastEthernet0\",\n" +
                "                                   \"mac-address\" : \"00:60:70:75:4e:01\"" +
                "                                }  " +
                                            "]" +
                "         }," +
                "        {" +
                "            \"network-element\":\n" +
                "            {" +
                "                \"name\" :\"nn-gagar-dsw1\","+
                "                    \"type\" :\"switch\"" +
                "            }," +
                "            \"interface\":" +
                "                   [" +
                "                           {" +
                "                               \"name\" :\"FastEthernet0/1\",\n" +
                "                                \"mac-address\" :\"00:90:2b:d0:55:01\"" +
                "                           }," +
                "                           {" +
                "                                \"name\" :\"FastEthernet0/2\",\n" +
                "                                   \"mac-address\" :\"00:90:2b:d0:55:02\"" +
                "                           }" +
                "                   ]" +
                "          }" +
                "    ]";

        //ClientResponse response = restService.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, str);
       // System.out.println("Response " + response.getEntity(String.class));
    }

    public void createSocket() {
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));

        SockJsClient sockJsClient = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String url = "ws://localhost:8080/network";
        userId = "spring-" + ThreadLocalRandom.current().nextInt(1, 99);
        StompSessionHandler sessionHandler = new MyStompSessionHandler(userId, this);
        try {
            session = stompClient.connect(url, sessionHandler).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }

    public void createModel(NetworkModel model){

        String neString = restService.path("network-elements").get(String.class);

        System.out.println(neString);
        List<NetworkElement> networkElements = null;
       // String linksString = restService.path("links").get(String.class);
       // List<Link> links = null;
        try {
            networkElements = mapper
                    .readValue(neString, new TypeReference<List<NetworkElement>>(){});
           // links = mapper.readValue(linksString, new TypeReference<List<Link>>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        model.setNetworkElements(networkElements);
       // model.setLinks(links);
        //add interfaces
    }
    public Boolean isNotBlocking(){
        return !isBlock;
    }
    public void sendMessage(Blocking status){
        session.send("/app/hello", new ClientMessage(userId, status.getValue()));
    }
}
