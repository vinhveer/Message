package org.vinhveer.message.Repository;

import org.vinhveer.message.Entity.Conversations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationsRepository extends MongoRepository<Conversations, String> {
}
