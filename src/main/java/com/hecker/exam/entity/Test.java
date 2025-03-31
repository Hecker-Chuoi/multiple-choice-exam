package com.hecker.exam.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Test {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    long testId;
    @NotEmpty(message = "Vui lòng nhập tên bài thi")
    String testName;
    String subject;
    LocalDateTime editedTime;
    @Builder.Default
    @Column(columnDefinition = "bit default 0")
    Boolean isDeleted = false;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "userId")
    @JsonIgnore
    @ToString.Exclude
    User author;

    @OneToMany(mappedBy = "test", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    List<Question> questions;
}
