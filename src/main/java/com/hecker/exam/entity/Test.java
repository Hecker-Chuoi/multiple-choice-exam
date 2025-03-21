package com.hecker.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
public class Test {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long testId;
    @NotEmpty(message = "Vui lòng nhập tên bài thi")
    String testName;
    String subject;
    LocalDateTime editedTime;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "userId")
    @JsonIgnore
    User author;

    @OneToMany(mappedBy = "test", orphanRemoval = true, cascade = CascadeType.ALL)
    List<Question> questions;
}
