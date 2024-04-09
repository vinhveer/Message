package org.vinhveer.message.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vinhveer.message.Entity.Message;

import java.util.List;

@Service
public interface MessageService {
    ResponseEntity<String> sendMessage(Message messages);
    ResponseEntity<String> deleteMessage(String messageId);
    ResponseEntity<String> editMessage(String messageId, String content);
    ResponseEntity<String> forwardMessage(String messageId, String conservationId);
    List<Message> getMessagesByConversationId(String conversationId);
}
