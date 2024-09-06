package com.example.bookshop.dto.users;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {
    @Size(min = 4 , message = "Minimum length must be 6")
    String username;

    @Size(min = 6 ,message = "Minimum length must be 6" )
    String password;

    String email;

    String phone;

    String firstName;

    String lastName;

    Integer age;

    Date birthDate;

    boolean gender;

    @Lob
    String image;

    List<String> roles;

}
