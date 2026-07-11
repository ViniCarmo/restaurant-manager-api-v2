package userType.application.useCases;

import org.springframework.stereotype.Component;
import userType.domain.UserType;
import userType.domain.repository.UserTypeRepository;

@Component
public class FindByNameUseCase {
    private final UserTypeRepository userTypeRepository;

    public FindByNameUseCase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public UserType execute(String name){
        return userTypeRepository.findByNameIgnoreCase(name).orElseThrow(()
                -> new IllegalArgumentException("User type with name " + name + " not found"));
    }
}
