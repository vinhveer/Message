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
public class Message {
    @Id
    private String id;
    private String conservationId;
    private String userId;
    private Type type;
    private String content;
    private long timestamp;

    public enum Type {
        TEXT,
        IMAGE,
        VIDEO,
        AUDIO,
        FILE,
        CALL
    }
}
