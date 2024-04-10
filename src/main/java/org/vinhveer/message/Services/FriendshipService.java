package org.vinhveer.message.Services;

import org.springframework.http.ResponseEntity;
import org.vinhveer.message.Entity.Friendship;

import java.util.List;

public interface FriendshipService {
    ResponseEntity<String> sendFriendRequest(String senderId, String receiverId);
    ResponseEntity<String> acceptFriendRequest(String requestId);
    ResponseEntity<String> rejectFriendRequest(String requestId);
    ResponseEntity<String> removeFriend(String friendshipId);
    List<Friendship> getFriendshipRequests(String userId);
    List<Friendship> getFriends(String userId);
}