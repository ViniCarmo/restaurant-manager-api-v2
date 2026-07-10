package user.application.useCases;

import org.springframework.stereotype.Component;
import user.domain.entity.User;
import user.domain.entity.UserRepository;

@Component
public class FindByEmailUseCase {

    private final UserRepository userRepository;

    public FindByEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String email) {
        return  userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

    }
}
