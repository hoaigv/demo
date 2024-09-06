package com.example.bookshop.controller;

import com.example.bookshop.dto.ApiResponse;
import com.example.bookshop.dto.role.RoleRequest;
import com.example.bookshop.dto.role.RoleResponse;
import com.example.bookshop.service.IRoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleController {
    IRoleService roleService;
    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest roleRequest) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(roleRequest))
                .build();
    }
    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build();
    }
    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role) {
        roleService.deleteRole(role);
        return ApiResponse.<Void>builder().build();
    }
    @PutMapping("/update")
    ApiResponse<RoleResponse> update(@RequestBody RoleRequest roleRequest) {
        var resp = roleService.update(roleRequest);
        return ApiResponse.<RoleResponse>builder()
                .result(resp)
                .build();
    }
}
