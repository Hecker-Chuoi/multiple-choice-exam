package com.hecker.exam.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateResult {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long testResultId;
    float timeTaken;
    float score;
    String status; //Not started / In progress / Completed
    LocalDateTime submitAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "candidate_id", referencedColumnName = "userId")
    User candidate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "session_id", referencedColumnName = "sessionId")
    TestSession testSession;

    @OneToMany(mappedBy = "candidateResult", orphanRemoval = true, cascade = CascadeType.ALL)
    List<CandidateAnswer> candidateAnswered;
}
