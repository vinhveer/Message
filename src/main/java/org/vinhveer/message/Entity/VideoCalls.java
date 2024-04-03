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
public class VideoCalls {
    @Id
    private String id;
    private String callerId;
    private String receiverId;
    private String status;
    private long startTime;
    private long endTime;
}
