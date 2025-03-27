package com.hecker.exam.mapper;

import com.hecker.exam.dto.request.session.SessionCreationRequest;
import com.hecker.exam.dto.request.session.SessionUpdateRequest;
import com.hecker.exam.entity.TestSession;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SessionMapper {
    TestSession createSession(SessionCreationRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSession(@MappingTarget TestSession session, SessionUpdateRequest request);
}
