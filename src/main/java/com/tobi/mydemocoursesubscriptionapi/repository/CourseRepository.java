package com.tobi.mydemocoursesubscriptionapi.repository;

import com.tobi.mydemocoursesubscriptionapi.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
