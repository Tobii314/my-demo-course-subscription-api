package com.tobi.mydemocoursesubscriptionapi.cervice;

import com.tobi.mydemocoursesubscriptionapi.exception.SubscriptionLimitException;
import com.tobi.mydemocoursesubscriptionapi.limitStrategy.SubscriptionLimitStrategy;
import com.tobi.mydemocoursesubscriptionapi.model.Course;
import com.tobi.mydemocoursesubscriptionapi.model.Subscription;
import com.tobi.mydemocoursesubscriptionapi.model.User;
import com.tobi.mydemocoursesubscriptionapi.model.status.CourseStatus;
import com.tobi.mydemocoursesubscriptionapi.model.status.UserStatus;
import com.tobi.mydemocoursesubscriptionapi.repository.CourseRepository;
import com.tobi.mydemocoursesubscriptionapi.repository.SubscriptionRepository;
import com.tobi.mydemocoursesubscriptionapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final Map<UserStatus, SubscriptionLimitStrategy> strategyMap;

    public SubscriptionService(SubscriptionRepository subscriptionRepository,
                               CourseRepository courseRepository, UserRepository userRepository,
                               List<SubscriptionLimitStrategy> strategies) {
        this.subscriptionRepository = subscriptionRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;

        this.strategyMap = new HashMap<>();
        for (SubscriptionLimitStrategy strategy: strategies) {
            this.strategyMap.put(strategy.getStatus(), strategy);
        }

    }

    public Subscription subscribeUserToCourse(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not found with id " + userId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new RuntimeException("Course not found with id " + courseId));

        SubscriptionLimitStrategy strategy = this.strategyMap.get(user.getUserStatus());
        int limit = strategy.getLimit();
        int activeCourses = subscriptionRepository.countByUserId_IdAndStatus(userId, CourseStatus.ACTIVE);
        if (activeCourses >= limit) {
            throw new SubscriptionLimitException("Unable to subscribe. Account limit exceeded!");
        }
        Subscription subscription = new Subscription();
        subscription.setStatus(CourseStatus.ACTIVE);
        subscription.setCourseId(course);
        subscription.setUserId(user);
        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Optional<Subscription> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    public boolean deleteSubscription(Long id) {
        if (subscriptionRepository.existsById(id)) {
            subscriptionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
