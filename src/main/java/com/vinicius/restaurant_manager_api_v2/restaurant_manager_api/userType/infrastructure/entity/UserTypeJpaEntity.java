package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_types")
@AllArgsConstructor
@NoArgsConstructor
@Getter

public class UserTypeJpaEntity {

        @Id
        private UUID id;

        @Column(nullable = false, unique = true)
        private String name;

}
