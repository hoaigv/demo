package com.example.bookshop.service.impl;

import com.example.bookshop.dto.request.PermissionRequest;
import com.example.bookshop.dto.response.PermissionResponse;
import com.example.bookshop.entity.PermissionEntity;
import com.example.bookshop.mapper.PermissionMapper;
import com.example.bookshop.repository.PermissionRepository;
import com.example.bookshop.service.IPermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService implements IPermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        PermissionEntity permission = permissionMapper.toPermissionEntity(request);
        permission =permissionRepository.save(permission);
           return permissionMapper.toPermissionResponse(permission);
     }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();
     return   permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }
    @Override
    public void  delete(String permission) {

        permissionRepository.deleteById(permission);
    }


}
