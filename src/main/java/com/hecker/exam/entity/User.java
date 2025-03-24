package com.hecker.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hecker.exam.entity.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

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
    @Column(unique = true)
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

//    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
//    List<Test> tests;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    List<CandidateResult> takenTests;

    @ManyToMany(mappedBy = "candidates", fetch = FetchType.LAZY)
    List<TestSession> assignedSessions;
}
