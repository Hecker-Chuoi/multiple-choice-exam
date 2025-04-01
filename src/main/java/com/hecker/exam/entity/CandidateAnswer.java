package com.hecker.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateAnswer {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long testAnswerId;
    @Column(length = 10)
    String answerChosen; // 0 for un selected, 1 for selected

    boolean isCorrect;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id", referencedColumnName = "questionId")
    @ToString.Exclude
    @JsonIgnore
    Question question;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "test_result_id", referencedColumnName = "testResultId")
    @ToString.Exclude
    @JsonIgnore
    CandidateResult candidateResult;
}
