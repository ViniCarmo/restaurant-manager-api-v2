package user.application.useCases;

import org.springframework.stereotype.Component;
import user.domain.entity.User;
import user.domain.entity.UserRepository;
import userType.domain.UserType;


@Component
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String name, String email, String password, UserType userType){
    if(userRepository.findByEmail(email).isPresent()){
        throw new RuntimeException("Email already in use.");
    }
        User user = User.create(name, email, password, userType);
        return userRepository.save(user);
    }
}
