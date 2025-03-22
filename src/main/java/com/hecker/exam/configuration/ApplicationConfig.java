package com.hecker.exam.configuration;

import com.hecker.exam.entity.enums.Role;
import com.hecker.exam.entity.User;
import com.hecker.exam.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicationConfig {
    PasswordEncoder encoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository repos) {
        return args -> {
            if(!repos.existsByUsername("admin")) {
                repos.save(User.builder()
                                .username("admin")
                                .password(encoder.encode("admin"))
                                .role(Role.ADMIN)
                        .build());
                log.warn("Admin user created with username: admin and password: admin");
            }
        };
    }
}
