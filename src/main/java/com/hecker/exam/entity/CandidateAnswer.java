package com.hecker.exam.entity;

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
    String answerChosen;

    boolean isCorrect;

    @OneToOne
    @JoinColumn(name = "question_id", referencedColumnName = "questionId")
    Question question;

    @ManyToOne(optional = false)
    @JoinColumn(name = "test_result_id", referencedColumnName = "testResultId")
    CandidateResult candidateResult;
}
