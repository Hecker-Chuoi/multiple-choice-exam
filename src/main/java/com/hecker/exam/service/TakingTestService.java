package com.hecker.exam.service;

import com.hecker.exam.dto.request.answer.CandidateAnswerRequest;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.entity.*;
import com.hecker.exam.entity.enums.TakingStatus;
import com.hecker.exam.exception.AppException;
import com.hecker.exam.repository.CandidateAnswerRepo;
import com.hecker.exam.repository.CandidateResultRepo;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TakingTestService {
    UserService userService;
    SessionService sessionService;
    CandidateResultRepo repo;
    CandidateAnswerRepo answerRepo;

    private void canAccess(long sessionId){
        User currentUser = userService.getMyInfo();
        TestSession session = sessionService.getSession(sessionId);
        CandidateResult result = repo.findByCandidateAndTestSession(currentUser, session).orElseThrow(
                () -> new AppException(StatusCode.SESSION_ACCESS_PERMISSION)
        );

        if(LocalDateTime.now().isBefore(session.getStartTime())){
            throw new AppException(StatusCode.START_THE_SESSION);
        }
        if(LocalDateTime.now().isAfter(session.getStartTime().plus(session.getTimeLimit()))
                || result.getStatus() == TakingStatus.COMPLETED){
            throw new AppException(StatusCode.TEST_COMPLETED);
        }
    }

    public Test getTest(long sessionId){
        User currentUser = userService.getMyInfo();
        TestSession session = sessionService.getSession(sessionId);

        repo.findByCandidateAndTestSession(currentUser, session).orElseThrow(
                () -> new AppException(StatusCode.SESSION_ACCESS_PERMISSION)
        );

        return session.getTest();
    }

    public List<Question> getQuestions(long sessionId){
        canAccess(sessionId);
        return getTest(sessionId).getQuestions();
    }

    public void startTest(Long sessionId) {
        User currentUser = userService.getMyInfo();
        TestSession session = sessionService.getSession(sessionId);

        CandidateResult result = findCandidateResult(currentUser, session);

        if(result.getStatus() != TakingStatus.NOT_STARTED){
            throw new AppException(StatusCode.ALREADY_STARTED);
        }
        if(LocalDateTime.now().isBefore(session.getStartTime())){
            throw new AppException(StatusCode.SESSION_NOT_STARTED);
        }
        if(LocalDateTime.now().isAfter(session.getStartTime().plus(session.getTimeLimit()))){
            throw new AppException(StatusCode.SESSION_EXPIRED);
        }

        result.setStatus(TakingStatus.IN_PROGRESS);
        repo.save(result);
    }

    private CandidateResult findCandidateResult(User candidate, TestSession session){
        return repo.findByCandidateAndTestSession(candidate, session).orElseThrow(
                () -> new AppException(StatusCode.SESSION_ACCESS_PERMISSION)
        );
    }

    private boolean isAnswerCorrect(Question question, String answer){
        return answer.equals(question.getCorrectAnswer());
    }

    private CandidateAnswer toCandidateAnswer(List<Question> questions, CandidateAnswerRequest request){
        Question question = null;
        for(Question q : questions){
            if(q.getQuestionId() == request.getQuestionId()){
                question = q;
                break;
            }
        }
        if(question == null){
            throw new AppException(StatusCode.QUESTION_NOT_FOUND);
        }

        return CandidateAnswer.builder()
                .answerChosen(request.getAnswerChosen())
                .question(question)
                .build();
    }

    @Transactional
    public void saveProgress(Long sessionId, List<CandidateAnswerRequest> answers){
        User currentUser = userService.getMyInfo();
        TestSession session = sessionService.getSession(sessionId);

        CandidateResult result = findCandidateResult(currentUser, session);
        if(result.getStatus() == TakingStatus.NOT_STARTED){
            throw new AppException(StatusCode.START_THE_SESSION);
        }
        if(result.getStatus() == TakingStatus.COMPLETED){
            throw new AppException(StatusCode.TEST_COMPLETED);
        }
        answerRepo.deleteByCandidateResult(result);

        Test test = session.getTest();
        for(CandidateAnswerRequest answer : answers){
            CandidateAnswer cAnswer = toCandidateAnswer(test.getQuestions(), answer);
            cAnswer.setCandidateResult(result);
            result.getCandidateAnswered().add(cAnswer);
        }

        repo.save(result);
    }

    private float calculateScore(List<CandidateAnswer> cAnswers){
        int right = 0;
        for(CandidateAnswer cAnswer : cAnswers){
            if(isAnswerCorrect(cAnswer.getQuestion(), cAnswer.getAnswerChosen())){
                right++;
            }
        }
        return (float) (1.0*right/cAnswers.size())*10;
    }

    public void submitTest(Long sessionId){
        User currentUser = userService.getMyInfo();
        TestSession session = sessionService.getSession(sessionId);
        
        CandidateResult result = findCandidateResult(currentUser, session);

        result.setSubmitAt(LocalDateTime.now());
        result.setStatus(TakingStatus.COMPLETED);
        result.setScore(calculateScore(result.getCandidateAnswered()));
//        result.setTimeTaken();
        
        repo.save(result);
    }

    public CandidateResult getResult(Long sessionId){
        User currentUser = userService.getMyInfo();
        TestSession session = sessionService.getSession(sessionId);
        return findCandidateResult(currentUser, session);
    }
}
