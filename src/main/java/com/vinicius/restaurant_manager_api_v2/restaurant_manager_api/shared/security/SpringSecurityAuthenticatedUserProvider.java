package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.shared.security;

import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.infrastructure.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SpringSecurityAuthenticatedUserProvider implements AuthenticatedUserProvider {

    @Override
    public UUID getLoggedUserId() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return userDetails.getDomainUser().getId();
    }

}
