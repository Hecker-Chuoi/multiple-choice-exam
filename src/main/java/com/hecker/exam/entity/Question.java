package com.hecker.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hecker.exam.entity.enums.QuestionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Question {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long questionId;
    @Length(max = 4000)
    String questionText;
    @Length(max = 4000)
    String explainText;
    @Column(length = 10)
    @JsonIgnore
    @ToString.Exclude
    String correctAnswer; // 0 for un selected, 1 for selected
    @Enumerated(EnumType.STRING)
    QuestionType questionType;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "test_id", referencedColumnName = "testId")
    @JsonIgnore
    @ToString.Exclude
    Test test;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    List<Answer> answers;
}
