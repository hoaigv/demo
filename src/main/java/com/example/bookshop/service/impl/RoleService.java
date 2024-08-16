package com.example.bookshop.service.impl;

import com.example.bookshop.dto.request.RoleRequest;
import com.example.bookshop.dto.response.RoleResponse;
import com.example.bookshop.entity.RoleEntity;
import com.example.bookshop.exception.CustomRunTimeException;
import com.example.bookshop.exception.ErrorCode;
import com.example.bookshop.mapper.RoleMapper;
import com.example.bookshop.repository.PermissionRepository;
import com.example.bookshop.repository.RoleRepository;
import com.example.bookshop.service.IRoleService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService implements IRoleService {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;
    RoleMapper roleMapper;

    @Override
    public List<RoleResponse> getAllRoles() {
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::roleToRoleResponse).toList();
    }

    @Override
    public RoleResponse create(RoleRequest request) {
       var role =  roleMapper.roleToRoleEntity(request);
       var permissions = permissionRepository.findAllById(request.getPermissions());
       role.setPermissions(new HashSet<>(permissions));
       roleRepository.save(role);
       return roleMapper.roleToRoleResponse(role);
    }

    @Override
    public void deleteRole(String role) {
        roleRepository.deleteById(role);
    }

    @Override
    @Transactional
    public RoleResponse update(RoleRequest request) {
        var oldRole =  roleRepository.findById(request.getName())
                .orElseThrow(()-> new CustomRunTimeException(ErrorCode.ROLE_NOT_FOUND));
        var permissions = permissionRepository.findAllById(request.getPermissions());
        oldRole.setPermissions(new HashSet<>(permissions));
        var role = new RoleEntity();
        role = roleMapper.roleRequestToRoleEntity(request ,oldRole);
        return roleMapper.roleToRoleResponse(roleRepository.save(role));


    }
}
