package org.vinhveer.message.Services.Impl;

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

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // Procedure for user
    private boolean userExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean userContainsNull(User user) {
        return user.getFullName() == null
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
        if (userExist(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        if (userContainsNull(user)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid information provided");
        }

        try {
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request" + e);
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
        if (!userExist(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        try {
            User existingUser = userRepository.findByEmail(user.getEmail());

            if (user.getFullName() != null) {
                existingUser.setFullName(user.getFullName());
            }
            if (user.getDateOfBirth() != null) {
                existingUser.setDateOfBirth(user.getDateOfBirth());
            }
            if (user.getAvatar() != null) {
                existingUser.setAvatar(user.getAvatar());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(user.getPassword());
            }
            if (user.isGender() != existingUser.isGender()) {
                existingUser.setGender(user.isGender());
            }

            userRepository.save(existingUser);

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
    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
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
