package com.example.bookshop.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "role")
@Table
public class RoleEntity {
    @Id
    String name;

    String description;

    @ManyToMany(mappedBy = "roles")
    Set<UserEntity> users = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
     @JoinTable(name = "role_permission",joinColumns = @JoinColumn(name = "role_id") ,inverseJoinColumns = @JoinColumn(name = "permission_id"))
    Set<PermissionEntity> permissions = new HashSet<>();

}
