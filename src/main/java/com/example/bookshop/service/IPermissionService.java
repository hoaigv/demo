package com.example.bookshop.service;

import com.example.bookshop.dto.permission.PermissionRequest;
import com.example.bookshop.dto.permission.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    void delete(String permission);
}
