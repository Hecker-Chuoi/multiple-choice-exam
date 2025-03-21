package com.hecker.exam.service;

import com.hecker.exam.dto.request.TestCreationRequest;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.entity.Question;
import com.hecker.exam.entity.Test;
import com.hecker.exam.exception.AppException;
import com.hecker.exam.mapper.TestMapper;
import com.hecker.exam.repository.TestRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Test createTest(TestCreationRequest request) {
        Test test = mapper.createTest(request);
        test.setEditedTime(LocalDateTime.now());
        test.setAuthor(userService.getMyInfo());
        return repos.save(test);
    }

    public Test getTest(long testId) {
        return repos.findById(testId).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Test> getAllTests() {
        return repos.findAll();
    }

    @Transactional
    public Test setQuestions(long testId, List<Question> questions) {
        Test test = repos.findById(testId).orElseThrow(() ->
                new AppException(StatusCode.TEST_NOT_FOUND));
        test.setQuestions(questions);
        return test;
    }
}
