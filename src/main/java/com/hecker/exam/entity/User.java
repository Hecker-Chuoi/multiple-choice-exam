package com.hecker.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hecker.exam.entity.enums.Gender;
import com.hecker.exam.entity.enums.Role;
import com.hecker.exam.entity.enums.Type;
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
    String password;
    String fullName;
    LocalDate dob;
    @Enumerated(EnumType.STRING)
    Gender gender;
    String phoneNumber;
    String mail;
    @Enumerated(EnumType.STRING)
    Type type;
    String hometown;
    @Enumerated(EnumType.STRING)
    Role role;
    @Builder.Default
    Boolean isDeleted = false;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    List<CandidateResult> takenTests;

    @ManyToMany(mappedBy = "candidates", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    List<TestSession> assignedSessions;
}
