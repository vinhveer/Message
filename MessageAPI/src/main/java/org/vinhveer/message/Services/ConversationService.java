package org.vinhveer.message.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vinhveer.message.Entity.Conversation;

import java.util.List;

@Service
public interface ConversationService {
    ResponseEntity<String> createConversation(Conversation conversation);
    ResponseEntity<String> deleteConversation(String conversationId);
    List<Conversation> getAllConversations();
    ResponseEntity<String> changeNameConversation(String conversationId, String name);
    ResponseEntity<String> addParticipant(String conversationId, String userId);
    ResponseEntity<String> removeParticipant(String conversationId, String userId);
    List<Conversation> getConversationByUserId(String userId);
}
