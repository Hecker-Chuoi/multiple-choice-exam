package com.hecker.exam.mapper;

import com.hecker.exam.dto.request.UserCreationRequest;
import com.hecker.exam.dto.request.UserUpdateRequest;
import com.hecker.exam.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User createUser(UserCreationRequest request);
    User updateUser(@MappingTarget User user, UserUpdateRequest request);
}
