package com.hecker.exam.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @NotBlank(message = "Vui lòng nhập tên đăng nhập")
    String username;
    @NotEmpty(message = "Mật khẩu phải dài từ 8 đến 20 ký tự")
    @Length(min = 8, max = 20, message = "Mật khẩu phải dài từ 8 đến 20 ký tự")
    String password;
    @NotEmpty(message = "Vui lòng nhập họ và tên")
    String fulName;
    LocalDate dob;
    String gender;
    String phoneNumber;
    String mail;
    String unit; // Đơn vị trong quân đội
    String hometown;
}
