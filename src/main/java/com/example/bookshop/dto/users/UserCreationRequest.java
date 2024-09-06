package com.example.bookshop.dto.users;

import com.example.bookshop.validator.DobConstraint;
import jakarta.validation.constraints.Pattern;
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

    @Pattern(regexp = "\\d{10,15}", message = "Invalid phone number")

    String phone;
    @Pattern(regexp = "[a-zA-Z ]+", message = "First name should not contain numbers or special characters")
    String firstName;
    @Pattern(regexp = "[a-zA-Z ]+", message = "Last name should not contain numbers or special characters")
    String lastName;

    Integer age;

     @DobConstraint(min = 18,message = "INVALID_DOB")
    LocalDate birthDate;

    boolean gender;

}
