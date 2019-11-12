package com.unn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.TextArea;
import org.springframework.messaging.simp.stomp.*;
import sample.ClientMessage;
import sample.ServerMessage;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private String userId;
    private TextArea textArea;
    private Integer isblock;
    ServerMessage mes;


    public MyStompSessionHandler(String userId, ServerMessage m) {
        this.userId = userId;
        mes = m;
    }

    private void showHeaders(StompHeaders headers) {
        for (Map.Entry<String, List<String>> e:headers.entrySet()) {
            System.err.print("  " + e.getKey() + ": ");
            boolean first = true;
            for (String v : e.getValue()) {
                if ( ! first ) System.err.print(", ");
                System.err.print(v);
                first = false;
            }
            System.err.println();
        }
    }

    private void sendJsonMessage(StompSession session) {
        ClientMessage msg = new ClientMessage(userId,3);
        session.send("/app/hello", msg);
    }

    private void subscribeTopic(String topic,StompSession session) {
        session.subscribe(topic, new StompFrameHandler() {

            public Type getPayloadType(StompHeaders headers) {
                return ServerMessage.class;
            }

            public void handleFrame(StompHeaders headers, Object payload) {
                System.err.println(payload.toString());

                ObjectMapper mapper = new ObjectMapper();
                try {
                    ServerMessage message = mapper.readValue(payload.toString(), ServerMessage.class);
                    if(message.getMessage() == 1){
                        mes.setMessage(1);
                    }
                    textArea.setText(textArea.getText() + " \n" + payload.toString());
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.err.println("Connected! Headers:");
        showHeaders(connectedHeaders);

        subscribeTopic("/topic/messages", session);
        sendJsonMessage(session);
    }
}
