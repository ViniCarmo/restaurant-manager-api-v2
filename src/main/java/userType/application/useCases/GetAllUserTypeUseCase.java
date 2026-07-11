package userType.application.useCases;

import org.springframework.stereotype.Component;
import userType.domain.UserType;
import userType.domain.repository.UserTypeRepository;

import java.util.List;

@Component
public class GetAllUserTypeUseCase {
    private final UserTypeRepository    userTypeRepository;

    public GetAllUserTypeUseCase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public List<UserType> execute() {
        return userTypeRepository.findAll();
    }
}
