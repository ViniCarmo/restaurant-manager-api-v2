package userType.application.useCases;

import org.springframework.stereotype.Component;
import userType.domain.UserType;
import userType.domain.repository.UserTypeRepository;

import java.util.UUID;

@Component
public class FindByIdUseCase {

    private final UserTypeRepository userTypeRepository;

    public FindByIdUseCase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public UserType execute(UUID id){
        return userTypeRepository.findById(id).orElseThrow(()
                -> new IllegalArgumentException("User type with ID " + id + " not found"));
    }
}
