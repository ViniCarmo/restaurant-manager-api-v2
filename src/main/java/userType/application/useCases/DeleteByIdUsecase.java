package userType.application.useCases;

import org.springframework.stereotype.Component;
import userType.domain.repository.UserTypeRepository;

import java.util.UUID;

@Component
public class DeleteByIdUsecase {
    private final UserTypeRepository userTypeRepository;

    public DeleteByIdUsecase(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public void execute(UUID id){
        userTypeRepository.findById(id).orElseThrow(() -> new RuntimeException("UserType not found"));
        userTypeRepository.deleteById(id);
    }
}
