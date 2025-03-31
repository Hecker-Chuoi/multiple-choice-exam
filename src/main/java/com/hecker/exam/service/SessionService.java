package com.hecker.exam.service;

import com.hecker.exam.dto.request.session.SessionCreationRequest;
import com.hecker.exam.dto.request.session.SessionUpdateRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.dto.response.TestResponse;
import com.hecker.exam.entity.*;
import com.hecker.exam.entity.enums.TakingStatus;
import com.hecker.exam.exception.AppException;
import com.hecker.exam.mapper.SessionMapper;
import com.hecker.exam.mapper.TestMapper;
import com.hecker.exam.repository.SessionRepository;
import com.hecker.exam.repository.TestRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionService {
    SessionRepository repo;
    TestRepository testRepo;
    UserService userService;
    SessionMapper mapper;

// Create
    public TestSession createSession(SessionCreationRequest request){
        TestSession session = mapper.toDTO(request);
        Test test = testRepo.findById(request.getTestId()).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND));
        session.setTest(test);
        session.setLastEditTime(LocalDateTime.now());
        return repo.save(session);
    }

// Read
    public TestSession getSession(Long sessionId){
        return repo.findById(sessionId).orElseThrow(() ->
                new AppException(StatusCode.SESSION_NOT_FOUND));
    }

    public List<TestSession> getAllSessions(String sortByField){
        try{
            return repo.findAllByIsDeleted(false, Sort.by(Sort.Direction.ASC, sortByField));
        }
        catch(Exception e){
            return repo.findAllByIsDeleted(false, Sort.by(Sort.Direction.ASC, "startTime"));
        }
    }

    public Test getTest(long sessionId){
        return getSession(sessionId).getTest();
    }

    public List<Question> getQuestions(long sessionId){
        return getTest(sessionId).getQuestions();
    }

    public List<User> getCandidates(long sessionId){
        return getSession(sessionId).getCandidates();
    }

    public List<CandidateResult> getCandidateResults(long sessionId){
        return getSession(sessionId).getCandidateResults();
    }

// Update
    public TestSession updateSession(long sessionId, SessionUpdateRequest request){
        TestSession session = getSession(sessionId);
        mapper.updateSession(session, request);

        session.setLastEditTime(LocalDateTime.now());
        return repo.save(session);
    }

    public TestSession changeTest(long sessionId, long testId){
        TestSession session = getSession(sessionId);
        Test test = testRepo.findById(testId).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND));
        if(test.getIsDeleted() || test.getQuestions().isEmpty())
            throw new AppException(StatusCode.TEST_NOT_VALID);

        session.setTest(test);
        return repo.save(session);
    }

    private void initCandidateResults(TestSession session){
        session.getCandidateResults().clear();
        List<User> candidates = session.getCandidates();
        for(User candidate : candidates){
            CandidateResult result = CandidateResult.builder()
                    .candidate(candidate)
                    .testSession(session)
                    .build();
            session.getCandidateResults().add(result);
        }
    }

    @Transactional
    public ApiResponse<String> candidateAssignByUsername(long sessionId, List<String> usernames){
        StringJoiner errors = new StringJoiner(", ");
        List<User> candidates = new ArrayList<>();
        for(String username : usernames){
            try {
                User candidate = userService.getUserByUsername(username);
                candidates.add(candidate);
            }
            catch(AppException e){
                errors.add(username);
            }
        }
        if(candidates.size() == usernames.size()){
            // Clear all candidates
            TestSession session = getSession(sessionId);
            session.getCandidates().clear();

            // Assign candidates
            session.setCandidates(candidates);
            initCandidateResults(session);
            repo.save(session);

            return ApiResponse.<String>builder()
                    .result("Candidates assigned successfully!")
                    .build();
        }
        return ApiResponse.<String>builder()
                .statusCode(StatusCode.CANDIDATE_NOT_FOUND.getCode())
                .message(StatusCode.CANDIDATE_NOT_FOUND.getMessage())
                .result("Candidates with the following usernames are not found: " + errors + ". All aborted.")
                .build();
    }

    @Transactional
    public ApiResponse<String> candidateAssignByTypes(long sessionId, List<String> types){
        // Clear all candidates
        TestSession session = getSession(sessionId);
        session.getCandidates().clear();

        // Assign candidates by types
        List<User> candidates = userService.getUsersByTypes(types);
        session.setCandidates(candidates);
        initCandidateResults(session);
        repo.save(session);

        return ApiResponse.<String>builder()
                .result("Candidates assigned successfully!")
                .build();
    }

// Delete
    public void deleteSession(long sessionId){
        getSession(sessionId).setIsDeleted(true);
    }
}
