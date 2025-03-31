package com.hecker.exam.dto.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hecker.exam.entity.enums.Gender;
import com.hecker.exam.entity.enums.Type;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @NotNull(message = "Vui lòng nhập ngày sinh")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    LocalDate dob;
    @NotNull
    Gender gender;
    String phoneNumber;
    @Email
    String mail;
    @NotNull
    Type type;
    String hometown;
}
