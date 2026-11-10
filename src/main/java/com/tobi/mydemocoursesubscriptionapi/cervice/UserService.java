package com.tobi.mydemocoursesubscriptionapi.cervice;

import com.tobi.mydemocoursesubscriptionapi.exception.IdenticalStatusException;
import com.tobi.mydemocoursesubscriptionapi.exception.UserNotFoundException;
import com.tobi.mydemocoursesubscriptionapi.model.User;
import com.tobi.mydemocoursesubscriptionapi.model.status.UserStatus;
import com.tobi.mydemocoursesubscriptionapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUserStatus(Long id, UserStatus userStatus) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id" + id));
        if (userStatus == user.getUserStatus()) {
            throw new IdenticalStatusException("Your status is already " + userStatus);
        }
        user.setUserStatus(userStatus);
        return userRepository.save(user);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
