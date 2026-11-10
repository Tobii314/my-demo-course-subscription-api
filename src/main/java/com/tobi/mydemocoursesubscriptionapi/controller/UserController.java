package com.tobi.mydemocoursesubscriptionapi.controller;

import com.tobi.mydemocoursesubscriptionapi.cervice.UserService;
import com.tobi.mydemocoursesubscriptionapi.dto.UpdateUserStatusRequestDto;
import com.tobi.mydemocoursesubscriptionapi.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PatchMapping("/{id}/status")
    public User updateUserStatus(@PathVariable Long id,
                                 @RequestBody UpdateUserStatusRequestDto requestDto) {
        return userService.updateUserStatus(id, requestDto.getStatus());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean isDeleted = userService.deleteUser(id);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
