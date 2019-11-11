package com.unn;

import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.graphstream.algorithm.generator.DorogovtsevMendesGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.basicRenderer.SwingBasicGraphRenderer;
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

import javax.swing.*;
import javax.websocket.*;
import java.awt.*;
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

    private WebSocketStompClient stompClient;
    private StompSession session;
    private String userId;

    @FXML
    private void initialize() {
        String styleSheet = "graph {padding: 20px;}";

        MultiGraph g = new MultiGraph("mg");
        Viewer v = new Viewer(g, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        DorogovtsevMendesGenerator gen = new DorogovtsevMendesGenerator();

        g.setAttribute("ui.antialias");
        g.setAttribute("ui.quality");
        g.setAttribute("ui.stylesheet", styleSheet);

        v.enableAutoLayout();
        JPanel panel = v.addView("1", new SwingBasicGraphRenderer());

        gen.addSink(g);
        gen.begin();
        for(int i = 0 ; i < 100 ; i++)
            gen.nextEvents();
        gen.end();
        gen.removeSink(g);
        final SwingNode swingNode = new SwingNode();

        swingNode.setContent(panel);
        graphPanel.getChildren().add(swingNode);
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
        StompSessionHandler sessionHandler = new MyStompSessionHandler(userId, textArea);
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
            System.out.flush();
            String line = null;
                line = text.getCharacters().toString();
            ClientMessage msg = new ClientMessage(userId, line);
            session.send("/app/hello", msg);
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
