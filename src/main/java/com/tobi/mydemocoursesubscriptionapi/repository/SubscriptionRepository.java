package com.tobi.mydemocoursesubscriptionapi.repository;

import com.tobi.mydemocoursesubscriptionapi.model.Subscription;
import com.tobi.mydemocoursesubscriptionapi.model.status.CourseStatus;
import com.tobi.mydemocoursesubscriptionapi.model.status.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    int countByUserId_IdAndStatus(Long userId, CourseStatus courseStatus);
}
