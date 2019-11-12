package com.unn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.unn.model.NetworkElement;
import com.unn.model.NetworkElementButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import javax.ws.rs.core.UriBuilder;
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

import javax.websocket.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {
    @FXML
    public Button btn1;
    @FXML
    public Button btn2;
    @FXML
    public TextField text;
    @FXML
    public  TextArea textArea;
    @FXML
    public Pane graphPanel;

    @FXML
    public FlowPane fPane;

    Integer isblock;
    ServerMessage m;

    private WebSocketStompClient stompClient;
    private StompSession session;
    private String userId;

    @FXML
    private void initialize() throws JsonProcessingException {

        m = new ServerMessage();
        m.setMessage(0);
        isblock=0;
        try {
            createSocket();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClientConfig config;
        config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource service = client.resource(UriBuilder.fromUri("http://localhost:8080/data/network-elements").build());
        System.out.println(service.get(String.class));
        ObjectMapper mapper = new ObjectMapper();

       List<NetworkElement> myObjects = mapper.readValue(service.get(String.class), new TypeReference<List<NetworkElement>>(){});
       myObjects.forEach(e-> {
           NetworkElementButton el = new NetworkElementButton(e.getName());
           el.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent event) {
                   if(m.getMessage() !=1) {
                       textArea.setText(el.getText());
                   }
               }
           });
          // el.setPrefSize(100,100);
           fPane.getChildren().addAll(el);
       });
//        HttpClient client = new DefaultHttpClient();
//        HttpGet request = new HttpGet("http://localhost:8080/data/network-elements");
//        HttpResponse response = null;
//        try {
//            response = client.execute(request);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        BufferedReader rd = null;
//        try {
//            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String line = "";
//        while (true) {
//            try {
//                if (!((line = rd.readLine()) != null)) break;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            System.out.println(line);
//        }

//        line = "[\"network-element\":" + line.subSequence(1, line.length());

//        JsonNode nodes = mapper.readTree(line);
//
//        for (JsonNode rootNode: nodes) {
//
//            JsonNode hw = rootNode.path("hw-component");
//            JsonNode ne = rootNode.path("network-element");
//            JsonNode inter = rootNode.path("interface");
//            JsonNode subInt = rootNode.path("sub-interface");
//
//            NetworkElement netElem = mapper.treeToValue(ne, NetworkElement.class);
//            Interface[] interfaces = mapper.treeToValue(inter, Interface[].class);
//
//
//            graphPanel.getChildren().addAll(new NetworkElementButton(netElem.getName()));
//            Interface in = null;
//            for (Interface i : interfaces) {
//                graphPanel.getChildren().addAll(new NetworkElementButton(i.getName()));
//            }
//
//        }

        ObjectMapper maper = new ObjectMapper();
        NetworkElement element;
        try {
            element =  maper.readValue("{\"name\":\"asr1\",\"type\":\"router\"}", NetworkElement.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            element = new NetworkElement();
        }
        //NetworkElement element = maper.convertValue("{name:\"asr1\";type:\"router\"}", NetworkElement.class);
        NetworkElementButton ne = new NetworkElementButton(element.getName());
        graphPanel.getChildren().addAll(ne);




//        String styleSheet = "graph {padding: 20px;}";
//
//        MultiGraph g = new MultiGraph("mg");
//        Viewer v = new Viewer(g, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
//        DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();
//
//        g.setAttribute("ui.antialias");
//        g.setAttribute("ui.quality");
//        g.setAttribute("ui.stylesheet", styleSheet);
//
//        v.enableAutoLayout();
//        JPanel panel = v.addView("1", new SwingBasicGraphRenderer());
//
//        gen.addSink(g);
//        gen.begin();
//        for(int i = 0 ; i < 100 ; i++)
//            gen.nextEvents();
//        gen.end();
//        gen.removeSink(g);
//        final SwingNode swingNode = new SwingNode();
//
//        swingNode.setContent(panel)
// graphPanel.getChildren().add(swingNode);
    }

    private void createSocket() throws ExecutionException, InterruptedException, IOException {
        WebSocketClient simpleWebSocketClient = new StandardWebSocketClient();
        List<Transport> transports = new ArrayList<>(1);
        transports.add(new WebSocketTransport(simpleWebSocketClient));

        SockJsClient sockJsClient = new SockJsClient(transports);
        stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        String url = "ws://localhost:8080/network";
        userId = "spring-" + ThreadLocalRandom.current().nextInt(1, 99);
        StompSessionHandler sessionHandler = new MyStompSessionHandler(userId, m);
        session = stompClient.connect(url, sessionHandler).get();

    }

    @FXML
    public void btnClick1(ActionEvent actionEvent) throws IOException, DeploymentException {

        try {
            createSocket();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void btnClick2(ActionEvent actionEvent) {
            //System.out.flush();
            //String line = null;
            //    line = text.getCharacters().toString();
           // ClientMessage msg = new ClientMessage(userId, line);
           // session.send("/app/hello", msg);
        session.send("/app/hello", new ClientMessage(userId, 1));

    }

    public void btnClick4(ActionEvent actionEvent) {
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://localhost:8080/data/network-elements");
        HttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader rd = null;
        try {
            rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line = "";
        while (true) {
            try {
                if (!((line = rd.readLine()) != null)) break;
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(line);
        }

    }

}
