package com.hecker.exam.repository;

import com.hecker.exam.entity.TestSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<TestSession, Long> {
}
