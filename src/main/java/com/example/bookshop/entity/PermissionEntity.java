package com.example.bookshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "permission")
@Table
public class PermissionEntity {
    @Id
    String name;

    String description;

    @ManyToMany(mappedBy = "permissions")
    Set<RoleEntity> users = new HashSet<>();


}
