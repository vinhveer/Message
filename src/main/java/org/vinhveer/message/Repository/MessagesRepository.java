package org.vinhveer.message.Repository;

import org.vinhveer.message.Entity.Messages;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends MongoRepository<Messages, String> {
}
