package userType.application.useCases;

import org.springframework.stereotype.Component;
import userType.domain.UserType;
import userType.domain.repository.UserTypeRepository;

@Component
public class CreateUserTypeUsecase {
    private final UserTypeRepository userTypeRepository;

    public CreateUserTypeUsecase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public UserType Execute(String name){
        if (userTypeRepository.existsByNameIgnoreCase(name)) {
            throw new RuntimeException("UserType with name " + name + " already exists");
        }

        UserType userType = UserType.create(name);

        return userTypeRepository.save(userType);
    }
}
