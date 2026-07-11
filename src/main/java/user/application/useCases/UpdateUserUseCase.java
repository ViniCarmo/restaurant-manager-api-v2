package user.application.useCases;

import org.springframework.stereotype.Component;
import user.domain.entity.User;
import user.domain.Repository.UserRepository;
import user.domain.exceptions.UserNotFoundException;

import java.util.UUID;

@Component
public class UpdateUserUseCase {

    private final UserRepository userRepository;

    public UpdateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UUID id, String name, String email) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.changeName(name);
        user.changeEmail(email);

        return userRepository.save(user);
    }
}
