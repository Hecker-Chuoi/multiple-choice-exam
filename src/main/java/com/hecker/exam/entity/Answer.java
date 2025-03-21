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
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long answerId;
    String answerText;
    boolean isCorrect;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", referencedColumnName = "questionId")
    Question question;
}
