package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security;

import java.util.UUID;

public interface AuthenticatedUserProvider {
    UUID getLoggedUserId();
}
