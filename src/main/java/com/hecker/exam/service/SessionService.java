package com.hecker.exam.service;

import com.hecker.exam.dto.request.SessionCreationRequest;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.entity.Test;
import com.hecker.exam.entity.TestSession;
import com.hecker.exam.entity.User;
import com.hecker.exam.exception.AppException;
import com.hecker.exam.mapper.SessionMapper;
import com.hecker.exam.repository.SessionRepository;
import com.hecker.exam.repository.TestRepository;
import com.hecker.exam.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionService {
    SessionRepository repo;
    TestRepository testRepo;
    UserRepository candidateRepo;
    SessionMapper mapper;

    public TestSession createSession(SessionCreationRequest request){
        TestSession session = mapper.createSession(request);
        Test test = testRepo.findById(request.getTestId()).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND));
        session.setTest(test);
        session.setLastEditTime(LocalDateTime.now());
        return repo.save(session);
    }

    public TestSession getSession(long sessionId){
        return repo.findById(sessionId).orElseThrow(() ->
                new AppException(StatusCode.SESSION_NOT_FOUND));
    }

    public TestSession updateSession(long sessionId, SessionCreationRequest request){
        TestSession session = getSession(sessionId);
        mapper.updateSession(session, request);
        Test test = testRepo.findById(request.getTestId()).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND));
        session.setTest(test);
        session.setLastEditTime(LocalDateTime.now());
        return repo.save(session);
    }

    public TestSession candidateAssign(long sessionId, List<Long> candidateIds){
        List<User> candidates = candidateRepo.findAllById(candidateIds);
        TestSession session = getSession(sessionId);

        session.setCandidates(candidates);
        return repo.save(session);
    }
}
