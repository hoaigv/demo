package com.example.bookshop.mapper;

import com.example.bookshop.dto.request.PermissionRequest;
import com.example.bookshop.dto.response.PermissionResponse;
import com.example.bookshop.entity.PermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    PermissionEntity toPermissionEntity(PermissionRequest permission);
    PermissionResponse toPermissionResponse(PermissionEntity permission);
}
