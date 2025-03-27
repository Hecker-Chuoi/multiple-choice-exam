package com.hecker.exam.mapper;

import com.hecker.exam.dto.response.ResultResponse;
import com.hecker.exam.entity.CandidateResult;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateResultMapper {
    ResultResponse toResponse(CandidateResult result);
    List<ResultResponse> toResponses(List<CandidateResult> results);
}
