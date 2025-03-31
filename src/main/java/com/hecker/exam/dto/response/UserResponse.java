package com.hecker.exam.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hecker.exam.entity.CandidateResult;
import com.hecker.exam.entity.Test;
import com.hecker.exam.entity.TestSession;
import com.hecker.exam.entity.enums.Gender;
import com.hecker.exam.entity.enums.Role;
import com.hecker.exam.entity.enums.Type;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserResponse {
    long userId;
    String username;
    String fullName;
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    LocalDate dob;
    Gender gender;
    String phoneNumber;
    String mail;
    Type type;
    String hometown;
    Role role;
}
