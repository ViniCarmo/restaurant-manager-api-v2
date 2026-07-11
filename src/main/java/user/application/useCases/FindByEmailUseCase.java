package user.application.useCases;

import org.springframework.stereotype.Component;
import user.domain.entity.User;
import user.domain.Repository.UserRepository;
import user.domain.exceptions.UserNotFoundException;

@Component
public class FindByEmailUseCase {

    private final UserRepository userRepository;

    public FindByEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String email) {
        return  userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new UserNotFoundException(email));

    }
}
