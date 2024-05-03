package org.vinhveer.message.Repository;

import org.vinhveer.message.Entity.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationsRepository extends MongoRepository<Conversation, String> {
    List<Conversation> findByParticipantId(String participantId);
}
