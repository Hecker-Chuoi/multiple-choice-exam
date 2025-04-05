package com.hecker.exam.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hecker.exam.utils.DurationSerializer;
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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    long sessionId;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    LocalDateTime lastEditTime;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", shape = JsonFormat.Shape.STRING)
    LocalDateTime startTime;
    @JsonSerialize(using = DurationSerializer.class)
    Duration timeLimit;
    @Builder.Default
    Boolean isDeleted = false;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", referencedColumnName = "testId")
    @JsonIgnore
    @ToString.Exclude
    Test test;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "test_candidate_assign",
        joinColumns = @JoinColumn(name = "session_id", referencedColumnName = "sessionId"),
        inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId")
    )
    @JsonIgnore
    @ToString.Exclude
    List<User> candidates;

    @OneToMany(mappedBy = "testSession", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    List<CandidateResult> candidateResults;
}
