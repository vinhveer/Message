package org.vinhveer.message.Services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.vinhveer.message.Entity.User;
import org.vinhveer.message.Repository.UserRepository;
import org.vinhveer.message.Services.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    // Procedure for user
    private boolean userExist(String email, String userId) {
        return userRepository.findByEmailAndId(email, userId) != null;
    }

    private boolean userContainsNull(User user) {
        return user.getId() == null || user.getFullName() == null
                || user.getEmail() == null || user.getPassword() == null
                || user.getDateOfBirth() == null || user.getAvatar() == null;
    }

    // Implementing UserService

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<String> createUser(User user) {
        if (userExist(user.getEmail(), user.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        if (userContainsNull(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid information provided");
        }

        try {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
    }

    @Override
    public ResponseEntity<String> deleteUser(String userId) {
        if (userRepository.findById(userId).orElse(null) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        try {
            userRepository.deleteById(userId);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
    }

    @Override
    public ResponseEntity<String> updateUser(User user) {
        if (!userExist(user.getEmail(), user.getId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        try {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body("User updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
    }

    @Override
    public ResponseEntity<String> checkUser(String email, String password) {
        if (email == null || password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email or password is missing");
        }

        try {
            User user = userRepository.findByEmail(email);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }

            if (user.getPassword().equals(password)) {
                return ResponseEntity.status(HttpStatus.OK).body("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
        }
    }

    @Override
    public List<User> getUserById(String userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.map(Collections::singletonList).orElse(Collections.emptyList());
    }

    @Override
    public List<User> findUserByKeyword(String keyword) {
        String lowercaseKeyword = keyword.toLowerCase();

        return userRepository.findAll()
                .stream()
                .filter(user -> {
                    String lowercaseFullName = user.getFullName().toLowerCase();
                    return lowercaseFullName.contains(lowercaseKeyword);
                })
                .collect(Collectors.toList());
    }

}
