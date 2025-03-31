package com.hecker.exam.mapper;

import com.hecker.exam.dto.request.session.SessionCreationRequest;
import com.hecker.exam.dto.request.session.SessionUpdateRequest;
import com.hecker.exam.dto.response.SessionResponse;
import com.hecker.exam.dto.response.TestResponse;
import com.hecker.exam.entity.TestSession;
import com.hecker.exam.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    @Mapping(target = "candidateCount", source = "candidates", qualifiedByName = "getCandidateCount")
    @Mapping(target = "testId", expression = "java(session.getTest().getTestId())")
    SessionResponse toResponse(TestSession session);

    @Mapping(target = "candidateCount", source = "candidates", qualifiedByName = "getCandidateCount")
    @Mapping(target = "testId", expression = "java(session.getTest().getTestId())")
    List<SessionResponse> toResponses(List<TestSession> sessions);

    TestSession toDTO(SessionCreationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSession(@MappingTarget TestSession session, SessionUpdateRequest request);

    @Named("getCandidateCount")
    default int getCandidateCount(List<User> candidates){
        return candidates != null ? candidates.size() : 0;
    }
}
