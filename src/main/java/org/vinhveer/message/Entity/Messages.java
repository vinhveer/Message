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
public class Messages {
    @Id
    private String id;
    private String conservationId;
    private String userId;
    private String type;
    private String content;
    private long timestamp;
}
