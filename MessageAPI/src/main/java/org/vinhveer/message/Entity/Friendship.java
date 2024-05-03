package org.vinhveer.message.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friendship {
    @Id
    private String id;
    private String senderId;
    private String receiverId;
    private FriendshipStatus status;
    private long timestamp;

    public enum FriendshipStatus {
        PENDING,
        ACCEPTED
    }
}
