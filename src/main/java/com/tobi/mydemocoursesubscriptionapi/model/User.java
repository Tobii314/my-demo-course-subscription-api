package com.tobi.mydemocoursesubscriptionapi.model;

import com.tobi.mydemocoursesubscriptionapi.model.status.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public User(String name, String email, String password, UserStatus userStatus) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userStatus = userStatus;
    }
}
