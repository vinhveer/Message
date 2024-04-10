package org.vinhveer.message.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vinhveer.message.Entity.Friendship;
import org.vinhveer.message.Services.FriendshipService;

import java.util.List;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    private final FriendshipService friendshipService;

    @Autowired
    public FriendshipController(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @PostMapping("/request")
    public ResponseEntity<String> sendFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        return friendshipService.sendFriendRequest(senderId, receiverId);
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptFriendRequest(@RequestParam String requestId) {
        return friendshipService.acceptFriendRequest(requestId);
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam String requestId) {
        return friendshipService.rejectFriendRequest(requestId);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFriend(@RequestParam String friendshipId) {
        return friendshipService.removeFriend(friendshipId);
    }

    @GetMapping("/requests")
    public List<Friendship> getFriendshipRequests(@RequestParam String userId) {
        return friendshipService.getFriendshipRequests(userId);
    }

    @GetMapping("/friends")
    public List<Friendship> getFriends(@RequestParam String userId) {
        return friendshipService.getFriends(userId);
    }
}