package com.example.bookshop.service;

import com.example.bookshop.dto.request.RoleRequest;
import com.example.bookshop.dto.response.RoleResponse;

import java.util.List;

public interface IRoleService {
    RoleResponse create(RoleRequest request);
    List<RoleResponse> getAllRoles();
    void deleteRole(String role);
    RoleResponse update(RoleRequest request);

}
