package com.example.bookshop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Table
@Entity(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserEntity extends BaseEntity {

    @Column( unique = true, columnDefinition = "VARCHAR(255) COLLATE utf8mb4_unicode_ci")
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role" , joinColumns = @JoinColumn(name = "user_id") ,inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<RoleEntity> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    List<CommentEntity> books = new ArrayList<>();



}
