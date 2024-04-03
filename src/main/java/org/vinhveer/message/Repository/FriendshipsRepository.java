package org.vinhveer.message.Repository;

import org.vinhveer.message.Entity.Friendships;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipsRepository extends MongoRepository<Friendships, String> {
}
