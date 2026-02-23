package az.developia.turbo_system_name.Project.Controller;



import az.developia.turbo_system_name.Project.response.ApiResponse;
import az.developia.turbo_system_name.Project.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PutMapping("/become-seller")
    public ApiResponse<String> becomeSeller(Authentication auth) {
        service.becomeSeller(auth.getName());
        return ApiResponse.ok("SELLER status verildi", "OK");
    }
}
