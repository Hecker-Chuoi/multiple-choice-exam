package com.hecker.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Answer {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long answerId;
    @Length(max = 4000)
    String answerText;
    Boolean isCorrect;

    @ManyToOne(optional = false)
    @JoinColumn(name = "question_id", referencedColumnName = "questionId")
    @JsonIgnore
    @ToString.Exclude
    Question question;
}
