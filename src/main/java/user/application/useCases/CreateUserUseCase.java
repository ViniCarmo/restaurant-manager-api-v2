package user.application.useCases;

import org.springframework.stereotype.Component;
import user.domain.entity.User;
import user.domain.Repository.UserRepository;
import user.domain.exceptions.EmailAlreadyInUseException;
import userType.domain.UserType;


@Component
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String name, String email, String password, UserType userType){
    if(userRepository.findByEmail(email).isPresent()){
        throw new EmailAlreadyInUseException(email);
    }
        User user = User.create(name, email, password, userType);
        return userRepository.save(user);
    }
}
