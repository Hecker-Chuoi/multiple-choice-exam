package com.hecker.exam.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @NotEmpty(message = "Vui lòng nhập họ và tên")
    String fullName;
    LocalDate dob;
    String gender;
    String phoneNumber;
    String mail;
    String unit; // Đơn vị trong quân đội
    String hometown;
}
