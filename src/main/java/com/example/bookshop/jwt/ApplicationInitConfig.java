package com.example.bookshop.jwt;

import com.example.bookshop.constant.PredefinedRole;
import com.example.bookshop.entity.RoleEntity;
import com.example.bookshop.entity.UserEntity;
import com.example.bookshop.repository.RoleRepository;
import com.example.bookshop.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

    PasswordEncoder passwordEncoder;

    @NonFinal
    static final String ADMIN_USER_NAME = "admin";

    @NonFinal
    static final String ADMIN_PASSWORD = "admin";

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        log.info("Initializing application.....");
        return args -> {
            if (userRepository.findByUsername(ADMIN_USER_NAME).isEmpty()) {
                roleRepository.save(RoleEntity.builder()
                        .name(PredefinedRole.USER)
                        .description("User role")
                        .build());

                RoleEntity adminRole = roleRepository.save(RoleEntity.builder()
                        .name(PredefinedRole.ADMIN)
                        .description("Admin role")
                        .build());

                var roles = new HashSet<RoleEntity>();
                roles.add(adminRole);

                UserEntity user = UserEntity.builder()
                        .username(ADMIN_USER_NAME)
                        .password(passwordEncoder.encode(ADMIN_PASSWORD))
                        .roles(roles)
                        .build();

                userRepository.save(user);
                log.warn("admin user has been created with default password: admin, please change it");
            }
            log.info("Application initialization completed .....");
        };
    }
}
