package com.hecker.exam.mapper;

import com.hecker.exam.dto.response.ResultResponse;
import com.hecker.exam.entity.CandidateResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateResultMapper {
    @Mapping(target = "candidateId", expression = "java(result.getCandidate().getUserId())")
    @Mapping(target = "sessionId", expression = "java(result.getTestSession().getSessionId())")
    ResultResponse toResponse(CandidateResult result);

    @Mapping(target = "candidateId", expression = "java(result.getCandidate().getUserId()")
    @Mapping(target = "sessionId", expression = "java(result.getTestSession().getSessionId())")
    List<ResultResponse> toResponses(List<CandidateResult> results);
}
