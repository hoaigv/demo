package com.example.bookshop.service;

import com.example.bookshop.dto.request.PermissionRequest;
import com.example.bookshop.dto.response.PermissionResponse;

import java.util.List;

public interface IPermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAllPermissions();
    void delete(String permission);
}
