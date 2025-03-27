package com.hecker.exam.service;

import com.hecker.exam.dto.request.session.SessionCreationRequest;
import com.hecker.exam.dto.request.session.SessionUpdateRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.dto.response.TestResponse;
import com.hecker.exam.entity.Test;
import com.hecker.exam.entity.TestSession;
import com.hecker.exam.entity.User;
import com.hecker.exam.entity.enums.Role;
import com.hecker.exam.exception.AppException;
import com.hecker.exam.mapper.SessionMapper;
import com.hecker.exam.mapper.TestMapper;
import com.hecker.exam.repository.SessionRepository;
import com.hecker.exam.repository.TestRepository;
import com.hecker.exam.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionService {
    SessionRepository repo;
    TestRepository testRepo;
    UserRepository candidateRepo;
    SessionMapper mapper;
    TestMapper testMapper;

    // Create a new session
    public TestSession createSession(SessionCreationRequest request){
        TestSession session = mapper.createSession(request);
        Test test = testRepo.findById(request.getTestId()).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND));
        session.setTest(test);
        session.setLastEditTime(LocalDateTime.now());
        return repo.save(session);
    }

    public TestSession getSession(Long sessionId){
        return repo.findById(sessionId).orElseThrow(() ->
                new AppException(StatusCode.SESSION_NOT_FOUND));
    }

    public List<TestSession> getAllSessions(){
        return repo.findAll();
    }

//    @Transactional
//    @Query("SELECT e FROM TestSession e JOIN FETCH e.test WHERE e.sessionId = :sessionId")
    public TestResponse getTest(long sessionId){
        return testMapper.toResponse(getSession(sessionId).getTest());
    }

    public List<User> getCandidates(long sessionId){
        return getSession(sessionId).getCandidates();
    }

    public TestSession changeTest(long sessionId, long testId){
        TestSession session = getSession(sessionId);
        Test test = testRepo.findById(testId).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND));
        session.setTest(test);
        return repo.save(session);
    }

    public TestSession updateSession(long sessionId, SessionUpdateRequest request){
        TestSession session = getSession(sessionId);
        mapper.updateSession(session, request);

        session.setLastEditTime(LocalDateTime.now());
        return repo.save(session);
    }

    public void deleteSession(long sessionId){
        getSession(sessionId).setDeleted(true);
    }

    @Transactional
    public ApiResponse<String> candidateAssign(long sessionId, List<String> usernames){
        StringJoiner result = new StringJoiner(", ");
        List<User> candidates = new ArrayList<>();
        for(String username : usernames){
            Optional<User> candidate = candidateRepo.findByUsername(username);
            if(candidate.isPresent() && candidate.get().getRole().equals(Role.USER)){
                candidates.add(candidate.get());
            } else {
                result.add(username);
            }
        }
        if(candidates.size() == usernames.size()){
            TestSession session = getSession(sessionId);
            session.setCandidates(candidates);
            repo.save(session);
            return ApiResponse.<String>builder()
                    .result("Candidates assigned successfully!")
                    .build();
        }
        return ApiResponse.<String>builder()
                .statusCode(StatusCode.CANDIDATE_NOT_FOUND.getCode())
                .message(StatusCode.CANDIDATE_NOT_FOUND.getMessage())
                .result("Candidates with the following usernames are not found: " + result + ". All aborted.")
                .build();
    }
}
