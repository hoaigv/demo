package com.example.bookshop.dto.users;

import com.example.bookshop.dto.role.RoleResponse;
import jakarta.persistence.Lob;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String username;

    String password;

    String email;

    String phone;

    String firstName;

    String lastName;

    Integer age;

    Date birthDate;

    boolean gender;

    boolean status;

    @Lob
    String image;

    Set<RoleResponse> roles ;
}
