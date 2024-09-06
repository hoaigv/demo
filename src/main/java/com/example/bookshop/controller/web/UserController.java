package com.example.bookshop.controller.web;

import com.example.bookshop.dto.ApiResponse;
import com.example.bookshop.dto.users.UserCreationRequest;
import com.example.bookshop.dto.users.UserResponse;

import com.example.bookshop.dto.users.UserUpdateRequest;
import com.example.bookshop.service.IUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController("WebUserController")
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    IUserService userService;

    @PostMapping(value = "/create")
    public ApiResponse<UserResponse> createUser(
            @RequestPart("data") @Valid  UserCreationRequest userCreationRequest,
            @RequestPart ("image") MultipartFile image) {
        var resp = userService.createUser(userCreationRequest,image);
        return ApiResponse.<UserResponse>builder()
                .result(resp )
                .build();
    }

    @PutMapping
   public  ApiResponse<UserResponse> updateUser(@RequestBody @Valid UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.updateUser(request))
                .build();
    }
    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }
    @PutMapping("/books/{bookId}")
    public ApiResponse<String> addFavorite(@PathVariable String bookId) {
        var resp = userService.addFavouriteBook(bookId);
        return ApiResponse.<String>builder()
                .result(resp)
                .build();
    }

    @PutMapping("/chapters/{chapterId}")
    public ApiResponse<Void> addReadChapter(@PathVariable String chapterId) {
         userService.addReadChapter(chapterId);
        return ApiResponse.<Void>builder()
                .build();
    }


}
