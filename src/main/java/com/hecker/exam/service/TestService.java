package com.hecker.exam.service;

import com.hecker.exam.dto.request.test.QuestionCreationRequest;
import com.hecker.exam.dto.request.test.TestCreationRequest;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.dto.response.TestResponse;
import com.hecker.exam.entity.Answer;
import com.hecker.exam.entity.Question;
import com.hecker.exam.entity.Test;
import com.hecker.exam.entity.enums.QuestionType;
import com.hecker.exam.exception.AppException;
import com.hecker.exam.mapper.TestMapper;
import com.hecker.exam.repository.TestRepository;
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
public class TestService {
    TestRepository repos;
    TestMapper mapper;
    UserService userService;
    private final TestRepository testRepository;

    public TestResponse createTest(TestCreationRequest request) {
        Test test = mapper.toTest(request);
        test.setEditedTime(LocalDateTime.now());
        test.setAuthor(userService.getMyInfo());
        return mapper.toResponse(repos.save(test));
    }

    public TestResponse getTest(long testId) {
        return mapper.toResponse(repos.findById(testId).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND)));
    }

    public List<TestResponse> getTestByDeleted(boolean isDeleted) {
        return mapper.toResponses(repos.findAllByDeleted(isDeleted));
    }

    public List<TestResponse> getAllTests() {
        return mapper.toResponses(repos.findAll());
    }

    public TestResponse updateTest(long testId, TestCreationRequest request) {
        Test test = repos.findById(testId).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND));
        mapper.updateTest(test, request);
        test.setEditedTime(LocalDateTime.now());
        return mapper.toResponse(repos.save(test));
    }

    public void deleteTest(long testId) {
        Test test = repos.findById(testId).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND));
        test.setEditedTime(LocalDateTime.now());
        test.setDeleted(true);
        repos.save(test);
    }

    public void restoreTest(long testId) {
        Test test = repos.findById(testId).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND));
        test.setEditedTime(LocalDateTime.now());
        test.setDeleted(false);
        repos.save(test);
    }

    private Question createQuestion(QuestionCreationRequest request){
        Question question = mapper.toQuestion(request);
        List<Answer> answers = mapper.toAnswers(request.getAnswerCreationRequests());

        StringBuilder correctAnswer = new StringBuilder();
        int cnt = 0;
        for(Answer answer : answers){
            if(answer.getIsCorrect()){
                cnt++;
            }
            correctAnswer.append(answer.getIsCorrect() ? "1" : "0");
        }

        if(question.getQuestionType() == QuestionType.SINGLE_CHOICE && cnt != 1
        || question.getQuestionType() == QuestionType.MULTIPLE_CHOICES && cnt < 1){
            throw new AppException(StatusCode.INVALID_NUMBERS_ANSWER);
        }
        answers.forEach(x -> x.setQuestion(question));
        question.setAnswers(answers);
        question.setCorrectAnswer(correctAnswer.toString());

        return question;
    }

    @Transactional
    public TestResponse setQuestions(long testId, List<QuestionCreationRequest> requests) {
        Test test = repos.findById(testId).orElseThrow(
                () -> new AppException(StatusCode.TEST_NOT_FOUND)
        );
        test.getQuestions().clear();

        for(QuestionCreationRequest request : requests){
            Question question = createQuestion(request);
            question.setTest(test);
            test.getQuestions().add(question);
        }

        return mapper.toResponse(testRepository.save(test));
    }
}
