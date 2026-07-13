package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.mapper;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.domain.entity.MenuItem;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.menuItem.presentation.dto.response.MenuItemResponseDto;

public class MenuItemPresentationMapper {
    public static MenuItemResponseDto toResponse(MenuItem menuItem){

        return new MenuItemResponseDto(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getDescription(),
                menuItem.getPrice(),
                menuItem.isDineInOnly(),
                menuItem.getPhotoPath(),
                menuItem.getRestaurant().getId(),
                menuItem.getRestaurant().getName(),
                menuItem.getCreatedAt(),
                menuItem.getUpdatedAt()
        );
    }
}
