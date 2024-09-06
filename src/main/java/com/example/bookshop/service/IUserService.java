package com.example.bookshop.service;

import com.example.bookshop.dto.users.UserCreationRequest;
import com.example.bookshop.dto.users.UserUpdateRequest;
import com.example.bookshop.dto.users.UserResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface IUserService {
    UserResponse getUserById(String id) ;
    List<UserResponse> getAllUsers(Pageable pageable);
    UserResponse getMyInfo();

    UserResponse createUser(UserCreationRequest request, MultipartFile file);
    UserResponse updateUser(UserUpdateRequest request);

    List<String> delete(Set<String> ids);

    String addFavouriteBook(String id);
    void  addReadChapter(String id);
}
