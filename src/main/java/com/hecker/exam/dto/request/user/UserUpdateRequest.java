package com.hecker.exam.dto.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.hecker.exam.entity.enums.Gender;
import com.hecker.exam.entity.enums.Type;
import com.hecker.exam.utils.DateDeserializer;
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
    @JsonDeserialize(using = DateDeserializer.class)
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
