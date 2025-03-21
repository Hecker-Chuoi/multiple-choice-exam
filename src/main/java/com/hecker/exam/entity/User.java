package com.hecker.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long userId;
    String username;
    @JsonIgnore
    String password;
    String fullName;
    LocalDate dob;
    String gender;
    String phoneNumber;
    String mail;
    String unit; // Đơn vị trong quân đội
    String hometown;
    Role role; // Candidate, Teacher, Admin

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    List<Test> tests;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    List<TestResult> takenTests;

    @ManyToMany(mappedBy = "candidates")
    List<TestSession> assignedSessions;

    public User(long userId, String username, String password, String fullName, LocalDate dob, String gender, String phoneNumber, String mail, String unit, String hometown, Role role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.dob = dob;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.unit = unit;
        this.hometown = hometown;
        this.role = role;
    }
}
