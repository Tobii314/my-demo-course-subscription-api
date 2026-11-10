package com.tobi.mydemocoursesubscriptionapi.cervice;

import com.tobi.mydemocoursesubscriptionapi.exception.IdenticalStatusException;
import com.tobi.mydemocoursesubscriptionapi.exception.UserNotFoundException;
import com.tobi.mydemocoursesubscriptionapi.limitStrategy.DefaultLimitStrategy;
import com.tobi.mydemocoursesubscriptionapi.limitStrategy.SubscriptionLimitStrategy;
import com.tobi.mydemocoursesubscriptionapi.limitStrategy.VipLimitStrategy;
import com.tobi.mydemocoursesubscriptionapi.model.Subscription;
import com.tobi.mydemocoursesubscriptionapi.model.User;
import com.tobi.mydemocoursesubscriptionapi.model.status.UserStatus;
import com.tobi.mydemocoursesubscriptionapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private User defaultUser;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void init() {
        defaultUser = new User();
        defaultUser.setId(1L);
    }

    @Test
    @DisplayName("Successfully update user status when status is not identical")
    public void shouldSuccessfullyUpdateUserStatus_whenStatusIsNotIdentical() {
        // Arrange
        defaultUser.setUserStatus(UserStatus.DEFAULT);
        when(userRepository.findById(1L)).thenReturn(Optional.of(defaultUser));

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> {
                    User user = invocation.getArgument(0);
                    return user;
                });
        // Act

        User updatedUser = userService.updateUserStatus(1L, UserStatus.VIP);

        // Assert

        assertNotNull(updatedUser);
        assertEquals(UserStatus.VIP, updatedUser.getUserStatus());

        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Throw an exception when the user is not found")
    public void shouldThrowException_whenUserNotFound() {
        // Arrange
        defaultUser.setUserStatus(UserStatus.DEFAULT);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());


        // Act + Assert
        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUserStatus(1L, UserStatus.VIP);
        });
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    @DisplayName("Throw an exception when the user has identical status")
    public void shouldThrowException_whenIdenticalStatus() {
        // Arrange
        defaultUser.setUserStatus(UserStatus.VIP);
        when(userRepository.findById(1L)).thenReturn(Optional.of(defaultUser));

        // Act + Assert
        assertThrows(IdenticalStatusException.class, () -> {
            userService.updateUserStatus(1L, UserStatus.VIP);
        });
        verify(userRepository, never()).save(any(User.class));
    }

}