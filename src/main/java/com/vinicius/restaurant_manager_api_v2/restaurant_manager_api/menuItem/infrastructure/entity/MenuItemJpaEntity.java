package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.infrastructure.entity;


import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.restaurant.infrastructure.entity.RestaurantJpaEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "menu_items")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MenuItemJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(nullable = false)
    private boolean dineInOnly;

    private String photoPath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private RestaurantJpaEntity restaurant;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
