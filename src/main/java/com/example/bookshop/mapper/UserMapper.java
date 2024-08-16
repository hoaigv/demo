package com.example.bookshop.mapper;

import com.example.bookshop.dto.request.UserCreationRequest;
import com.example.bookshop.dto.request.UserUpdateRequest;
import com.example.bookshop.dto.response.UserResponse;
import com.example.bookshop.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
   UserResponse userToUserResponse(UserEntity userEntity);
   UserEntity userToUserEntity(UserCreationRequest userCreationRequest);
   @Mapping(target = "roles" , ignore = true)
   UserEntity updateUserEntity(@MappingTarget UserEntity userEntity, UserUpdateRequest userUpdateRequest);

}
