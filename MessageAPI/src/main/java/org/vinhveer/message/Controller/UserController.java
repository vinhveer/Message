package org.vinhveer.message.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vinhveer.message.Entity.User;
import org.vinhveer.message.Services.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUser() {
        return userService.getAllUsers();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String userId) {
        return userService.deleteUser(userId);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PostMapping("/check")
    public ResponseEntity<String> checkUser(@RequestParam String email, @RequestParam String password) {
        return userService.checkUser(email, password);
    }

    @GetMapping("/get_id")
    public List<User> getUserById(@RequestParam String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping("/get_email")
    public User getUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email).orElse(null);
    }

    @GetMapping("/search")
    public List<User> findUserByName(@RequestParam String keyword) {
        return userService.findUserByKeyword(keyword);
    }
}
