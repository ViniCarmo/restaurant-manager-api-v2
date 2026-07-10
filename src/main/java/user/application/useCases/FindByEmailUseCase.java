package user.application.useCases;

import org.springframework.stereotype.Component;
import user.domain.entity.User;
import user.domain.entity.UserRepository;
import user.domain.exceptions.UserNotFoundException;

@Component
public class FindByEmailUseCase {

    private final UserRepository userRepository;

    public FindByEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String email) {
        return  userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(email));

    }
}
