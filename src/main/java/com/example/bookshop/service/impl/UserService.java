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
import com.example.bookshop.repository.BookRepository;
import com.example.bookshop.repository.ChapterRepository;
import com.example.bookshop.repository.RoleRepository;
import com.example.bookshop.repository.UserRepository;
import com.example.bookshop.service.IUserService;
import com.example.bookshop.utils.AuthUtils;
import com.example.bookshop.utils.CloudUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService implements IUserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    BookRepository bookRepository;
    ChapterRepository chapterRepository;
    CloudUtils cloudinary;

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
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent())
                .orElseThrow(() -> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        return userMapper.userToUserResponse(user);
    }
    @Override
    @Transactional
    public UserResponse createUser(UserCreationRequest request , MultipartFile  image) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new CustomRunTimeException(ErrorCode.USER_EXISTED);
        }
        UserEntity user = userMapper.userToUserEntity(request);
        user.setStatus(true);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        HashSet<RoleEntity> roles = new HashSet<>();
        roleRepository.findById(PredefinedRole.USER).ifPresent(roles::add);
        user.setRoles(roles);
        if (image.isEmpty()) {
            throw new IllegalArgumentException("File không có dữ liệu");
        }
        var link = cloudinary.uploadFile(image);

        user.setImage(link);
        var resp = new UserResponse();
        try {
             resp  = userMapper.userToUserResponse(userRepository.save(user));
        } catch (DataIntegrityViolationException exception) {
            throw new DataIntegrityViolationException(exception.getMessage());
        }
        return resp;
    }

    @Override
    @Transactional
    public UserResponse updateUser( UserUpdateRequest request) {
        UserEntity oldUser = userRepository.findByUsername(AuthUtils.getUserCurrent()).orElseThrow(()->new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        var roles = roleRepository.findAllById(request.getRoles());
        oldUser.setRoles(new HashSet<>(roles));
        oldUser.setPassword(passwordEncoder.encode(request.getPassword()));
        var user = userMapper.updateUserEntity(oldUser, request);
        return userMapper.userToUserResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public List<String> delete(Set<String> ids) {
        log.info("In method delete");
        List<String> deletedIds = new ArrayList<>();
        var oldUsers = userRepository.findAllById(ids);
        oldUsers.forEach(user -> user.setStatus(false));
        var newUsers = oldUsers.stream().map(userRepository::save);
        newUsers.forEach(user -> deletedIds.add(user.getUsername()));
        return deletedIds;
    }
    @Override
    @Transactional
    public String addFavouriteBook(String id) {
        var bookEntity = bookRepository.findById(id).orElseThrow(
                () -> new CustomRunTimeException(ErrorCode.BOOK_NOT_FOUND)
        );
        var user = userRepository.findByUsername(AuthUtils.getUserCurrent())
                .orElseThrow(()-> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
        user.getBooks().add(bookEntity);
        userRepository.save(user);
        return bookEntity.getTitle();
    }

    @Override
    @Transactional
    public void addReadChapter(String id) {
       var chapter = chapterRepository.findById(id).orElseThrow(
               () -> new CustomRunTimeException(ErrorCode.CHAPTER_NOT_FOUND)
       );
       var user = userRepository.findByUsername(AuthUtils.getUserCurrent())
               .orElseThrow(()-> new CustomRunTimeException(ErrorCode.USER_NOT_FOUND));
       user.getChapters().add(chapter);
       userRepository.save(user);
    }
}
