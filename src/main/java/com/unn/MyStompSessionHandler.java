package com.unn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.unn.model.Service;
import org.springframework.messaging.simp.stomp.*;
import sample.ClientMessage;
import sample.ServerMessage;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;

public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private String userId;
    private Service service;

    public MyStompSessionHandler(String userId, Service service) {
        this.userId = userId;
        this.service = service;
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
        ClientMessage msg = new ClientMessage(userId,6, "", "");
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
                    switch (message.getStatus()){
                        case 1: {
                            if(!message.getFrom().equals(userId)){
                                service.getLockingElements().add(message.getIdNeA());
                            }
                            break;
                        }
                        case 2:{
                            service.getLockingElements().remove(message.getIdNeA());
                            break;
                        }
                        case 3:{
                            service.getLockingElements().add(message.getIdNeA());
                            service.getLockingElements().add(message.getIdNeZ());
                            break;
                        }
                        case 4:{
                            service.getLockingElements().remove(message.getIdNeA());
                            service.getLockingElements().remove(message.getIdNeZ());
                            break;
                        }
                        case 5:{
                            service.getModel().updateFromServer();
                            break;
                        }

                    }
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
