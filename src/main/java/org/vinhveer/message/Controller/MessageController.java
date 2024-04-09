package org.vinhveer.message.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vinhveer.message.Entity.Message;
import org.vinhveer.message.Services.MessageService;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody Message messages) {
        return messageService.sendMessage(messages);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteMessage(@RequestParam String messageId) {
        return messageService.deleteMessage(messageId);
    }

    @PostMapping("/edit")
    public ResponseEntity<String> editMessage(@RequestParam String messageId, String content) {
        return messageService.editMessage(messageId, content);
    }

    @PostMapping("/forward")
    public ResponseEntity<String> forwardMessage(@RequestParam String messageId, String conservationId) {
        return messageService.forwardMessage(messageId, conservationId);
    }

    @GetMapping("/get")
        public List<Message> getMessagesByConversationId(@RequestParam String conversationId) {
        return messageService.getMessagesByConversationId(conversationId);
    }
}
