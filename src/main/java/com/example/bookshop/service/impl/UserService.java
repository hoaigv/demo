package com.example.bookshop.service.impl;

import com.example.bookshop.constant.PredefinedRole;
import com.example.bookshop.dto.users.UserCreationRequest;
import com.example.bookshop.dto.users.UserUpdateRequest;
import com.example.bookshop.dto.users.UserResponse;
import com.example.bookshop.entity.RoleEntity;
import com.example.bookshop.entity.UserEntity;
import com.example.bookshop.exception.CustomRunTimeException;
import com.example.bookshop.exception.ErrorCode;
import com.example.bookshop.mapper.UserMapper;
import com.example.bookshop.repository.RoleRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.IUserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService implements IUserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(String id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.userToUserResponse(userEntity);
    }
    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers(Pageable pageable) {
        log.info("In method getAllUsers");
        return userRepository.findAll(pageable)
                .stream().map(userMapper::userToUserResponse).toList();
    }
    @Override
    @Transactional(readOnly = true)
    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        return userMapper.userToUserResponse(user);
    }
    @Override
    @Transactional
    public UserResponse createUser(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomRunTimeException(ErrorCode.USER_EXISTED);
        }
        UserEntity user = userMapper.userToUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<RoleEntity> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER).ifPresent(roles::add);
        user.setRoles(roles);
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException(exception.getMessage());
        }
        return userMapper.userToUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        UserEntity oldUser = userRepository.findById(userId).orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var roles = roleRepository.findAllById(request.getRoles());
        oldUser.setRoles(new HashSet<>(roles));
        oldUser.setPassword(passwordEncoder.encode(request.getPassword()));
        var user = new UserEntity();
        user = userMapper.updateUserEntity(oldUser, request);
        return userMapper.userToUserResponse(userRepository.save(user));
    }

}
