package org.vinhveer.message.Services;

import org.springframework.http.ResponseEntity;
import org.vinhveer.message.Entity.Friendship;

import java.util.List;

public interface FriendshipService {
    ResponseEntity<String> sendFriendRequest(String senderId, String receiverId);
    ResponseEntity<String> acceptFriendRequest(String senderId, String receiverId);
    ResponseEntity<String> rejectFriendRequest(String senderId, String receiverId);
    ResponseEntity<String> removeFriend(String friendshipId);
    List<Friendship> getFriendshipRequests(String receiverId);
    List<Friendship> getFriends(String userId);
}