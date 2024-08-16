package com.example.bookshop.service;

import com.example.bookshop.dto.request.UserCreationRequest;
import com.example.bookshop.dto.request.UserUpdateRequest;
import com.example.bookshop.dto.response.UserResponse;

import java.util.List;

public interface IUserService {
    UserResponse getUserById(String id) ;
    List<UserResponse> getAllUsers();
    UserResponse getMyInfo();

    UserResponse createUser(UserCreationRequest request);
    UserResponse updateUser(String userId,UserUpdateRequest request);
}
