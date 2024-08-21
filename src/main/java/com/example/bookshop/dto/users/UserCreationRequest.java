package com.example.bookshop.dto.users;

import com.example.bookshop.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    @Size(min = 6 , message = "Minimum length must be 6")
    String username;

    @Size(min = 6 ,message = "Minimum length must be 6" )
    String password;

    String email;

    String phone;

    String firstName;

    String lastName;

    Integer age;

     @DobConstraint(min = 18,message = "INVALID_DOB")
    LocalDate birthDate;

    boolean gender;

}
