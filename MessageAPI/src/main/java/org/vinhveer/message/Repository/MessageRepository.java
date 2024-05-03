package org.vinhveer.message.Repository;

import org.vinhveer.message.Entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {
    List<Message> findByConservationId(String conservationId);
}
