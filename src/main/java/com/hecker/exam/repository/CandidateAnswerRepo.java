package com.hecker.exam.repository;

import com.hecker.exam.entity.CandidateAnswer;
import com.hecker.exam.entity.CandidateResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateAnswerRepo extends JpaRepository<CandidateAnswer, Long> {
    @Modifying
    @Query("DELETE FROM CandidateAnswer ca WHERE ca.candidateResult = :candidateResult")
    void deleteByCandidateResult(@Param("candidateResult") CandidateResult candidateResult);

}
