package com.hecker.exam.repository;

import com.hecker.exam.entity.CandidateResult;
import com.hecker.exam.entity.TestSession;
import com.hecker.exam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateResultRepo extends JpaRepository<CandidateResult, Long> {
    Optional<CandidateResult> findByCandidateAndTestSession(User candidate, TestSession testSession);
}
