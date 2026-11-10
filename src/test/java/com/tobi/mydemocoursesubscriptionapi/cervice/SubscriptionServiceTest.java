package com.tobi.mydemocoursesubscriptionapi.cervice;

import com.tobi.mydemocoursesubscriptionapi.exception.CourseNotFoundException;
import com.tobi.mydemocoursesubscriptionapi.exception.SubscriptionLimitException;
import com.tobi.mydemocoursesubscriptionapi.exception.UserNotFoundException;
import com.tobi.mydemocoursesubscriptionapi.limitStrategy.DefaultLimitStrategy;
import com.tobi.mydemocoursesubscriptionapi.limitStrategy.SubscriptionLimitStrategy;
import com.tobi.mydemocoursesubscriptionapi.model.Course;
import com.tobi.mydemocoursesubscriptionapi.model.Subscription;
import com.tobi.mydemocoursesubscriptionapi.model.User;
import com.tobi.mydemocoursesubscriptionapi.model.status.CourseStatus;
import com.tobi.mydemocoursesubscriptionapi.model.status.UserStatus;
import com.tobi.mydemocoursesubscriptionapi.repository.CourseRepository;
import com.tobi.mydemocoursesubscriptionapi.repository.SubscriptionRepository;
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
class SubscriptionServiceTest {

    private User defaultUser;
    private Course defaultCourse;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CourseRepository courseRepository;

    //@InjectMocks
    private SubscriptionService subscriptionService;

    @BeforeEach
    public void init() {
        List<SubscriptionLimitStrategy> strategies = List.of(new DefaultLimitStrategy());
        subscriptionService = new SubscriptionService(
                subscriptionRepository, courseRepository, userRepository, strategies);
        defaultUser = new User();
        defaultUser.setId(1L);
        defaultUser.setUserStatus(UserStatus.DEFAULT);
        defaultCourse = new Course();
        defaultCourse.setId(2L);
    }

    @Test
    @DisplayName("Successful user subscription when the limit has not been exceeded")
    public void shouldSuccessfullySubscribeUser_whenLimitNotExceeded() {
        // arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(defaultUser));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(defaultCourse));
        when(subscriptionRepository.countByUserId_IdAndStatus(1L, CourseStatus.ACTIVE))
                .thenReturn(1);

        when(subscriptionRepository.save(any(Subscription.class)))
                .thenAnswer(invocation -> {
                    Subscription subscription = invocation.getArgument(0);
                    subscription.setId(999L);
                    return subscription;
                });

        // act
        Subscription result = subscriptionService.subscribeUserToCourse(1L, 2L);

        // assert

        assertNotNull(result);
        assertEquals(999L, result.getId());
        assertEquals(CourseStatus.ACTIVE, result.getStatus());
        assertEquals(1L, result.getUserId().getId());
        assertEquals(2L, result.getCourseId().getId());
        verify(subscriptionRepository, times(1)).save(any(Subscription.class));

    }

    @Test
    @DisplayName("Throw an exception when the user is not found")
    public void shouldThrowException_whenUserNotFound() {
        // arrange
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //act + assert
        assertThrows(UserNotFoundException.class, () -> {
            subscriptionService.subscribeUserToCourse(1L, 2L);
        });
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    @DisplayName("Throw an exception when the course is not found")
    public void shouldThrowException_whenCourseNotFound() {
        // arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(defaultUser));
        when(courseRepository.findById(2L)).thenReturn(Optional.empty());

        //act + assert
        assertThrows(CourseNotFoundException.class, () -> {
            subscriptionService.subscribeUserToCourse(1L, 2L);
        });
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }

    @Test
    @DisplayName("Throw an exception when the subscription limit exceeded")
    public void shouldThrowException_whenSubscriptionLimitExceeded() {

        //arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(defaultUser));
        when(courseRepository.findById(2L)).thenReturn(Optional.of(defaultCourse));

        when(subscriptionRepository.countByUserId_IdAndStatus(1L, CourseStatus.ACTIVE))
                .thenReturn(999);

        //act + assert
        assertThrows(SubscriptionLimitException.class, () -> {
            subscriptionService.subscribeUserToCourse(1L, 2L);
        });
        verify(subscriptionRepository, never()).save(any(Subscription.class));
    }
}