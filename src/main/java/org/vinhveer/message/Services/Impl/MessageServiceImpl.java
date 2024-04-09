package org.vinhveer.message.Services.Impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vinhveer.message.Entity.Conversation;
import org.vinhveer.message.Entity.Message;
import org.vinhveer.message.Entity.User;
import org.vinhveer.message.Repository.ConversationsRepository;
import org.vinhveer.message.Repository.MessageRepository;
import org.vinhveer.message.Repository.UserRepository;
import org.vinhveer.message.Services.MessageService;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ConversationsRepository conversationsRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository,
                              ConversationsRepository conversationsRepository,
                              UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.conversationsRepository = conversationsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<String> sendMessage(Message messages) {
        Optional<Conversation> conversationOptional = conversationsRepository.findById(messages.getConservationId());
        if (conversationOptional.isEmpty())
            return ResponseEntity.badRequest().body("Conversation not found");

        Optional<User> userOptional = userRepository.findById(messages.getUserId());
        if (userOptional.isEmpty())
            return ResponseEntity.badRequest().body("Sender not found");

        if (messages.getContent() == null || messages.getType() == null)
            return ResponseEntity.badRequest().body("Invalid message content");

        if (messages.getContent().isEmpty())
            return ResponseEntity.badRequest().body("Message content cannot be empty");

        try {
            messages.setTimestamp(System.currentTimeMillis());
            messageRepository.save(messages);
            return ResponseEntity.ok("Message sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request" + e);
        }
    }

    @Override
    public ResponseEntity<String> deleteMessage(String messageId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isEmpty())
            return ResponseEntity.badRequest().body("Message not found");

        try {
            messageRepository.deleteById(messageId);
            return ResponseEntity.ok("Message deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request" + e);
        }
    }

    @Override
    public ResponseEntity<String> editMessage(String messageId, String content) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isEmpty())
            return ResponseEntity.badRequest().body("Message not found");

        if (content == null) {
            return ResponseEntity.badRequest().body("Invalid message content");
        }

        try {
            Message message = messageOptional.get();
            message.setContent(content);
            messageRepository.save(message);
            return ResponseEntity.ok("Message edited successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request" + e);
        }
    }

    @Override
    public ResponseEntity<String> forwardMessage(String messageId, String conservationId) {
        Optional<Message> messageOptional = messageRepository.findById(messageId);
        if (messageOptional.isEmpty())
            return ResponseEntity.badRequest().body("Message not found");

        Optional<Conversation> conversationOptional = conversationsRepository.findById(conservationId);
        if (conversationOptional.isEmpty())
            return ResponseEntity.badRequest().body("Conversation not found");

        try {
            Message message = messageOptional.get();
            message.setConservationId(conservationId);
            messageRepository.save(message);
            return ResponseEntity.ok("Message forwarded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Bad request" + e);
        }
    }

    @Override
    public List<Message> getMessagesByConversationId(String conversationId) {
        return messageRepository.findByConservationId(conversationId);
    }
}
