package org.vinhveer.message.Repository;

import org.vinhveer.message.Entity.CallParticipants;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallPartcipantsRepository extends MongoRepository<CallParticipants, String> {
}
