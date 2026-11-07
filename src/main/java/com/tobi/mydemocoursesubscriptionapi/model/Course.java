package com.tobi.mydemocoursesubscriptionapi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="courses")
@Getter
@Setter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private double price;

    public Course(String name, String content, double price) {
        this.name = name;
        this.content = content;
        this.price = price;
    }
}
