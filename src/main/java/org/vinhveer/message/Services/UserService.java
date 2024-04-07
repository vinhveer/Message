package org.vinhveer.message.Services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vinhveer.message.Entity.User;

import java.util.List;

@Service
public interface UserService {
    ResponseEntity<String> createUser(User user);
    ResponseEntity<String> deleteUser(String userId);
    List<User> getAllUsers();
    ResponseEntity<String> updateUser(User user);
    ResponseEntity<String> checkUser(String email, String password);
    List<User> getUserById(String userId);
    List<User> findUserByKeyword(String keyword);
}
