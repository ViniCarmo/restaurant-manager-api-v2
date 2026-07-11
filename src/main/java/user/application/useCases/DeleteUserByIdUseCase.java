package user.application.useCases;

import org.springframework.stereotype.Component;
import user.domain.entity.User;
import user.domain.Repository.UserRepository;
import user.domain.exceptions.UserNotFoundException;

import java.util.UUID;

@Component
public class DeleteUserByIdUseCase {
    private final UserRepository userRepository;

    public DeleteUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID id) {
        userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        userRepository.deleteById(id);
    }
}
