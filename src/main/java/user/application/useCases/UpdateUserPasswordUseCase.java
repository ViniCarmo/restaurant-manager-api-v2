package user.application.useCases;

import user.domain.entity.User;
import user.domain.entity.UserRepository;

import java.util.UUID;

public class UpdateUserPasswordUseCase {

    private final UserRepository userRepository;

    public UpdateUserPasswordUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(UUID id, String newPassword){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.updatePassword(newPassword);
        userRepository.save(user);
    }
}
