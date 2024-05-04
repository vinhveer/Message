package org.vinhveer.message.Repository;

import org.vinhveer.message.Entity.Friendship;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipRepository extends MongoRepository<Friendship, String> {
    // Update method names and parameters to use new field names
    List<Friendship> findBySenderId(String senderId);
    Friendship findBySenderIdAndReceiverId(String senderId, String receiverId);

    List<Friendship> findByReceiverIdAndStatus(String receiverId, Friendship.FriendshipStatus friendshipStatus);

    // This method combines queries for sender and receiver status, so parameters need to be adjusted accordingly.
    List<Friendship> findBySenderIdAndStatusOrReceiverIdAndStatus(String senderId, Friendship.FriendshipStatus senderStatus, String receiverId, Friendship.FriendshipStatus receiverStatus);
}
