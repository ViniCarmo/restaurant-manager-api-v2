package com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.application.useCases;

import org.springframework.stereotype.Component;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.entity.User;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.Repository.UserRepository;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.user.domain.exceptions.EmailAlreadyInUseException;
import com.vinicius.restaurant_manager_api_v2.restaurant_manager_api.userType.domain.entity.UserType;


@Component
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String name, String email, String password, UserType userType){
    if(userRepository.findByEmailIgnoreCase(email).isPresent()){
        throw new EmailAlreadyInUseException(email);
    }
        User user = User.create(name, email, password, userType);
        return userRepository.save(user);
    }
}
