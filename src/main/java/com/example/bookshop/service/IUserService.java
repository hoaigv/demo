package com.example.bookshop.service;

import com.example.bookshop.dto.users.UserCreationRequest;
import com.example.bookshop.dto.users.UserUpdateRequest;
import com.example.bookshop.dto.users.UserResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IUserService {
    UserResponse getUserById(String id) ;
    List<UserResponse> getAllUsers(Pageable pageable);
    UserResponse getMyInfo();

    UserResponse createUser(UserCreationRequest request);
    UserResponse updateUser(String userId,UserUpdateRequest request);
}
