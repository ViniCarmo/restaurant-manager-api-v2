package user.application.useCases;

import org.springframework.stereotype.Component;
import user.domain.entity.User;
import user.domain.entity.UserRepository;

import java.util.UUID;

@Component
public class FindByIdUseCase {

    private final UserRepository userRepository;


    public FindByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
