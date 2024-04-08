package org.vinhveer.message.Services.Impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vinhveer.message.Entity.Conversation;
import org.vinhveer.message.Repository.ConversationsRepository;
import org.vinhveer.message.Repository.UserRepository;
import org.vinhveer.message.Services.ConversationService;

import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {
    private final ConversationsRepository conversationsRepository;
    private final UserRepository userRepository;

    public ConversationServiceImpl(ConversationsRepository conversationsRepository, UserRepository userRepository) {
        this.conversationsRepository = conversationsRepository;
        this.userRepository = userRepository;
    }

    // Function
    private boolean conversationContainsNull(Conversation conversation) {
        return conversation.getConservationName() == null || conversation.getParticipantId() == null;
    }


    // Override ConversationService

    @Override
    public ResponseEntity<String> createConversation(Conversation conversation) {
        if (conversationContainsNull(conversation)) {
            return ResponseEntity.badRequest().body("Invalid information provided");
        }

        try {
            conversationsRepository.save(conversation);
            return ResponseEntity.ok("Conversation created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request" + e);
        }
    }

    @Override
    public ResponseEntity<String> deleteConversation(String conversationId) {

        if (conversationsRepository.findById(conversationId).isEmpty()) {
            return ResponseEntity.badRequest().body("Conversation not found");
        }
        try {
            conversationsRepository.deleteById(conversationId);
            return ResponseEntity.ok("Conversation deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request" + e);
        }
    }

    @Override
    public List<Conversation> getAllConversations() {
        return conversationsRepository.findAll();
    }

    @Override
    public ResponseEntity<String> changeNameConversation(String conversationId, String new_name) {
        if (conversationsRepository.findById(conversationId).isEmpty()) {
            return ResponseEntity.badRequest().body("Conversation not found");
        }

        try {
            Conversation conversation = conversationsRepository.findById(conversationId).get();
            conversation.setConservationName(new_name);
            conversationsRepository.save(conversation);
            return ResponseEntity.ok("Conversation name changed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request" + e);
        }
    }


    @Override
    public ResponseEntity<String> addParticipant(String conversationId, String participantId) {
        if (conversationsRepository.findById(conversationId).isEmpty()) {
            return ResponseEntity.badRequest().body("Conversation not found");
        }

        if (userRepository.findById(participantId).isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        try {
            Conversation conversation = conversationsRepository.findById(conversationId).get();
            conversation.getParticipantId().add(participantId);
            conversationsRepository.save(conversation);
            return ResponseEntity.ok("User added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request" + e);
        }
    }

    @Override
    public ResponseEntity<String> removeParticipant(String conversationId, String participantId) {
        if (conversationsRepository.findById(conversationId).isEmpty()) {
            return ResponseEntity.badRequest().body("Conversation not found");
        }

        if (userRepository.findById(participantId).isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        try {
            Conversation conversation = conversationsRepository.findById(conversationId).get();
            conversation.getParticipantId().remove(participantId);
            conversationsRepository.save(conversation);
            return ResponseEntity.ok("User removed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request" + e);
        }
    }

    @Override
    public List<Conversation> getConversationByUserId(String userId) {
        return conversationsRepository.findByParticipantId(userId);
    }
}
