package org.vinhveer.message.Services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.vinhveer.message.Entity.Friendship;
import org.vinhveer.message.Repository.FriendshipRepository;
import org.vinhveer.message.Services.FriendshipService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendshipRepository friendshipRepository;

    @Autowired
    public FriendshipServiceImpl(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @Override
    public ResponseEntity<String> sendFriendRequest(String senderId, String receiverId) {
        Optional<Friendship> existingFriendship = Optional.ofNullable(friendshipRepository.findBySenderIdAndReceiverId(senderId, receiverId));
        if (existingFriendship.isPresent()) {
            return ResponseEntity.badRequest().body("A friendship or request already exists between these users.");
        }

        Friendship newFriendship = new Friendship();
        newFriendship.setSenderId(senderId);
        newFriendship.setReceiverId(receiverId);
        newFriendship.setStatus(Friendship.FriendshipStatus.PENDING);
        newFriendship.setTimestamp(System.currentTimeMillis());

        friendshipRepository.save(newFriendship);

        return ResponseEntity.ok("Friend request sent successfully.");
    }
    private List<Friendship> getPendingFriendships(String senderID) {
        List<Friendship> friendships = friendshipRepository.findBySenderId(senderID);

        if (friendships.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Friend request not found");
        }

        return friendships.stream()
                .filter(friendship -> friendship.getStatus() == Friendship.FriendshipStatus.PENDING)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<String> acceptFriendRequest(String senderId, String receiverId) {
        try {
            List<Friendship> friendships = getPendingFriendships(senderId);

            for (Friendship friendship : friendships) {
                if (!friendship.getReceiverId().equals(receiverId)) {
                    return ResponseEntity.badRequest().body("User is not authorized to accept this friend request");
                }

                friendship.setStatus(Friendship.FriendshipStatus.ACCEPTED);
                friendshipRepository.save(friendship);
            }

            return ResponseEntity.ok("friend request have been accepted successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.badRequest().body(e.getReason());
        }
    }

    @Override
    public ResponseEntity<String> rejectFriendRequest(String senderId, String receiverId) {
        try {
            List<Friendship> friendships = getPendingFriendships(senderId);

            for (Friendship friendship : friendships) {
                if (!friendship.getReceiverId().equals(receiverId)) {
                    return ResponseEntity.badRequest().body("User is not authorized to reject this friend request");
                }

                friendshipRepository.delete(friendship);
            }

            return ResponseEntity.ok("All friend requests from this sender have been rejected successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.badRequest().body(e.getReason());
        }
    }

    @Override
    public ResponseEntity<String> removeFriend(String friendshipId) {
        Optional<Friendship> friendshipOptional = friendshipRepository.findById(friendshipId);

        if (friendshipOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Friendship not found");
        }

        Friendship friendship = friendshipOptional.get();

        friendshipRepository.delete(friendship);

        return ResponseEntity.ok("Friend removed successfully");
    }


    @Override
    public List<Friendship> getFriendshipRequests(String receiverId) {
        return friendshipRepository.findByReceiverIdAndStatus(receiverId, Friendship.FriendshipStatus.PENDING);
    }



    @Override
    public List<Friendship> getFriends(String userId) {
        return friendshipRepository.findBySenderIdAndStatusOrReceiverIdAndStatus(userId, Friendship.FriendshipStatus.ACCEPTED, userId, Friendship.FriendshipStatus.ACCEPTED);
    }


}