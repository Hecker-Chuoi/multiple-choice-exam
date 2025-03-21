package com.hecker.exam.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestSession {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long sessionId;
    LocalDateTime lastEditTime;
    LocalDateTime startTime;
    Duration timeLimit;

    @ManyToOne(optional = false)
    @JoinColumn(name = "test_id", referencedColumnName = "testId")
    Test test;

    @ManyToMany
    @JoinTable(
        name = "test_candidate_assign",
        joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "sessionId"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId")
    )
    List<User> candidates;
}
