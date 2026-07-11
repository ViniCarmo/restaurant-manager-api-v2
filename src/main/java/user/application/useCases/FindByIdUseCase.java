package user.application.useCases;

import org.springframework.stereotype.Component;
import user.domain.entity.User;
import user.domain.Repository.UserRepository;
import user.domain.exceptions.UserNotFoundException;

import java.util.UUID;

@Component
public class FindByIdUseCase {

    private final UserRepository userRepository;


    public FindByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id){
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
