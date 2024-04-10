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
    public ResponseEntity<String> acceptFriendRequest(@RequestParam String userId, @RequestParam String requestId) {
        return friendshipService.acceptFriendRequest(userId, requestId);
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam String userId, @RequestParam String requestId) {
        return friendshipService.rejectFriendRequest(userId, requestId);
    }

    @DeleteMapping("/remove/{friendshipId}")
    public ResponseEntity<String> removeFriend(@PathVariable String friendshipId) {
        return friendshipService.removeFriend(friendshipId);
    }

    @GetMapping("/requests/{userId}")
    public List<Friendship> getFriendshipRequests(@PathVariable String userId) {
        return friendshipService.getFriendshipRequests(userId);
    }

    @GetMapping("/friends/{userId}")
    public List<Friendship> getFriends(@PathVariable String userId) {
        return friendshipService.getFriends(userId);
    }
}