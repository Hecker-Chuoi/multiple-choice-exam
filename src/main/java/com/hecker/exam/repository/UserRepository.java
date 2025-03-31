package com.hecker.exam.repository;

import com.hecker.exam.entity.enums.Role;
import com.hecker.exam.entity.User;
import com.hecker.exam.entity.enums.Type;
import jakarta.persistence.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    List<User> findByRoleAndIsDeleted(Role role, Boolean isDeleted);
    List<User> findByTypeAndIsDeleted(Type type, Boolean isDeleted);
}
