package org.vinhveer.message.RestController;

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
    public ResponseEntity<String> acceptFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        return friendshipService.acceptFriendRequest(senderId, receiverId);
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectFriendRequest(@RequestParam String senderId, @RequestParam String receiverId) {
        return friendshipService.rejectFriendRequest(senderId, receiverId);
    }

    @DeleteMapping("/remove/{friendshipId}")
    public ResponseEntity<String> removeFriend(@PathVariable String friendshipId) {
        return friendshipService.removeFriend(friendshipId);
    }

    @GetMapping("/requests/{receiverId}")
    public List<Friendship> getFriendshipRequests(@PathVariable String receiverId) {
        return friendshipService.getFriendshipRequests(receiverId);
    }

    @GetMapping("/friends/{userId}")
    public List<Friendship> getFriends(@PathVariable String userId) {
        return friendshipService.getFriends(userId);
    }
}