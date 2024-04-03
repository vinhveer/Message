package org.vinhveer.message.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.ArrayList;
@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Conversations {
    @Id
    private String id;
    private List<String> participants = new ArrayList<>();
    private List<String> messages = new ArrayList<>();
}
