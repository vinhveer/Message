package org.vinhveer.message.Repository;

import org.vinhveer.message.Entity.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends MongoRepository<Friendship, String> {
    Friendship findByUserId(String userId);
    Friendship findByUserIdAndFriendId(String userId, String friendId);

    List<Friendship> findByFriendIdAndStatus(String userId, Friendship.FriendshipStatus friendshipStatus);

    List<Friendship> findByUserIdAndStatusOrFriendIdAndStatus(String userId, Friendship.FriendshipStatus friendshipStatus, String userId1, Friendship.FriendshipStatus friendshipStatus1);
}
