package org.vinhveer.message.Auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String fullName;
    private boolean gender;
    private String dateOfBirth;
    private String email;
    private String avatar;
    private String password;
}
