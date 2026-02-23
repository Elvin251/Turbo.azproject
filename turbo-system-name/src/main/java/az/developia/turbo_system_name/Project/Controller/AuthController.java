package az.developia.turbo_system_name.Project.Controller;





import az.developia.turbo_system_name.Project.request.LoginRequest;
import az.developia.turbo_system_name.Project.request.RegisterRequest;
import az.developia.turbo_system_name.Project.response.ApiResponse;
import az.developia.turbo_system_name.Project.response.AuthResponse;
import az.developia.turbo_system_name.Project.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ApiResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest req) {
        return ApiResponse.ok("Qeydiyyat ugurludur", service.register(req));
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest req) {
        return ApiResponse.ok("Login ugurludur", service.login(req));
    }
}