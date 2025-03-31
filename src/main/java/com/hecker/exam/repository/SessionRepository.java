package com.hecker.exam.repository;

import com.hecker.exam.entity.TestSession;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<TestSession, Long> {
    List<TestSession> findAllByIsDeleted(Boolean isDeleted, Sort sort);
}
