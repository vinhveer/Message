package org.vinhveer.message.Repository;

import org.vinhveer.message.Entity.VideoCalls;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoCallsRepository extends MongoRepository<VideoCalls, String> {
}
