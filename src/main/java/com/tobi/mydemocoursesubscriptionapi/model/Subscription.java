package com.tobi.mydemocoursesubscriptionapi.model;


import com.tobi.mydemocoursesubscriptionapi.model.status.CourseStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@NoArgsConstructor
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // link to user primary key
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    //link to course primary key
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course courseId;

    @Column
    @Enumerated(EnumType.STRING)
    private CourseStatus status;
}
