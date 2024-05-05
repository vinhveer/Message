package org.vinhveer.message.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.vinhveer.message.Entity.Message;
import org.vinhveer.message.Services.MessageService;

@Component
public class MessageWebSocketHandler extends TextWebSocketHandler {
    private final MessageService messageService;
    private final ObjectMapper objectMapper;

    public MessageWebSocketHandler(MessageService messageService, ObjectMapper objectMapper) {
        this.messageService = messageService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("WebSocket connection established: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Message msg = objectMapper.readValue(message.getPayload(), Message.class);
        messageService.sendMessage(msg);
        session.sendMessage(new TextMessage("Message sent successfully"));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
