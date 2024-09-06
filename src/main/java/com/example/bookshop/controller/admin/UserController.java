package com.example.bookshop.controller.admin;

import com.example.bookshop.dto.ApiResponse;
import com.example.bookshop.dto.users.UserResponse;
import com.example.bookshop.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController("AdminUserController")
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    IUserService userService;

    private  final  static String DEFAULT_FILTER_LIMIT = "20";
    private  final  static   String DEFAULT_FILTER_OFFSET = "0";
    private  final  static Sort DEFAULT_FILTER_SORT = Sort.by(Sort.Direction.DESC , "createDate");
    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers(
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_LIMIT  ) int limit,
            @RequestParam(required = false, defaultValue = DEFAULT_FILTER_OFFSET) int offset
    ) {
        Pageable pageable = PageRequest.of(offset, limit, DEFAULT_FILTER_SORT);
        var resp = userService.getAllUsers(pageable);
        return ApiResponse.<List<UserResponse>>builder()
                .result(resp)
                .build();
    }

    @DeleteMapping
    ApiResponse<String> deleteUser(@RequestBody Set<String> userId) {
        return ApiResponse.<String>builder()
                .build();
    }
    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserById(userId))
                .build();
    }


}
