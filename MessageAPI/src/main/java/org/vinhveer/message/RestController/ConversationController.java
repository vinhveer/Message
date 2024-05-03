package org.vinhveer.message.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vinhveer.message.Entity.Conversation;
import org.vinhveer.message.Services.ConversationService;

import java.util.List;

@RestController
@RequestMapping("/conversation")
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    // Implementing ConversationService
    // Create conversation
    @PostMapping("/create")
    public ResponseEntity<String> createConversation(@RequestBody Conversation conversation) {
        return conversationService.createConversation(conversation);
    }

    // Delete conversation
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteConversation(@RequestParam String conversationId) {
        return conversationService.deleteConversation(conversationId);
    }

    // Get all conversations
    @GetMapping("/all")
    public List<Conversation> getAllConversations() {
        return conversationService.getAllConversations();
    }

    @PostMapping("/changeName")
    public ResponseEntity<String> changeNameConversation(@RequestParam String conversationId, String new_name) {
        return conversationService.changeNameConversation(conversationId, new_name);
    }

    @PostMapping("/addParticipant")
    public ResponseEntity<String> addParticipant(@RequestParam String conversationId, String participantId) {
        return conversationService.addParticipant(conversationId, participantId);
    }

    @PostMapping("/removeParticipant")
    public ResponseEntity<String> removeParticipant(@RequestParam String conversationId, String participantId) {
        return conversationService.removeParticipant(conversationId, participantId);
    }

    @GetMapping("/getByUserId")
    public List<Conversation> getConversationByUserId(@RequestParam String userId) {
        return conversationService.getConversationByUserId(userId);
    }
}
