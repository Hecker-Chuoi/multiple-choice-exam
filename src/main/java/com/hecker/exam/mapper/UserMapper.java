package com.hecker.exam.mapper;

import com.hecker.exam.dto.request.auth.UserCreationRequest;
import com.hecker.exam.dto.request.auth.UserUpdateRequest;
import com.hecker.exam.dto.response.UserResponse;
import com.hecker.exam.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User createUser(UserCreationRequest request);
    User updateUser(@MappingTarget User user, UserUpdateRequest request);
    UserResponse toResponse(User user);
    List<UserResponse> toResponses(List<User> users);
}
