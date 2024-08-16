package com.example.bookshop.mapper;

import com.example.bookshop.dto.request.RoleRequest;
import com.example.bookshop.dto.response.RoleResponse;
import com.example.bookshop.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    @Mapping(target = "permissions" , ignore = true)
    RoleEntity roleToRoleEntity(RoleRequest request);

    RoleResponse roleToRoleResponse(RoleEntity role);

    @Mapping(target = "permissions" , ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "name", source = "name")
    RoleEntity roleRequestToRoleEntity(RoleRequest request , @MappingTarget RoleEntity role);
}
