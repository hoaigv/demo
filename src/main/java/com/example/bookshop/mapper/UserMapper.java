package com.example.bookshop.mapper;

import com.example.bookshop.dto.users.UserCreationRequest;
import com.example.bookshop.dto.users.UserUpdateRequest;
import com.example.bookshop.dto.users.UserResponse;
import com.example.bookshop.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
   UserResponse userToUserResponse(UserEntity userEntity);

   @Mapping(target = "image", ignore = true)
   UserEntity userToUserEntity(UserCreationRequest userCreationRequest);


   @Mapping(target = "roles" , ignore = true)
   @Mapping(target = "image", ignore = true)
   UserEntity updateUserEntity(@MappingTarget UserEntity userEntity, UserUpdateRequest userUpdateRequest);

}
