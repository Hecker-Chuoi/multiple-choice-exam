package com.hecker.exam.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hecker.exam.entity.enums.TakingStatus;
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
    @JsonFormat(pattern = "2.2f", shape = JsonFormat.Shape.STRING)
    float timeTaken;
    @JsonFormat(pattern = "2.2f", shape = JsonFormat.Shape.STRING)
    float score;
    @Enumerated(EnumType.STRING)
    @Builder.Default
    TakingStatus status = TakingStatus.NOT_STARTED; //Not started / In progress / Completed
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime submitAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "candidate_id", referencedColumnName = "userId")
    @ToString.Exclude
    @JsonIgnore
    User candidate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", referencedColumnName = "sessionId")
    @ToString.Exclude
    @JsonIgnore
    TestSession testSession;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "candidateResult", orphanRemoval = true, cascade = CascadeType.ALL)
    List<CandidateAnswer> candidateAnswered;
}
