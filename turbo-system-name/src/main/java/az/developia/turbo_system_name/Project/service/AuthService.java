package az.developia.turbo_system_name.Project.service;


import az.developia.turbo_system_name.Project.entity.RoleEntity;
import az.developia.turbo_system_name.Project.entity.UserEntity;
import az.developia.turbo_system_name.Project.exception.BadRequestException;
import az.developia.turbo_system_name.Project.repository.RoleRepository;
import az.developia.turbo_system_name.Project.request.LoginRequest;
import az.developia.turbo_system_name.Project.request.RegisterRequest;
import az.developia.turbo_system_name.Project.response.AuthResponse;
import az.developia.turbo_system_name.Project.security.JwtService;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserService userService;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public AuthService(UserService userService, RoleRepository roleRepo,
                       PasswordEncoder encoder, AuthenticationManager authManager,
                       JwtService jwtService) {
        this.userService = userService;
        this.roleRepo = roleRepo;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    // Tapşırıq: qeydiyyat -> avtomatik BUYER
    public AuthResponse register(RegisterRequest req) {
        if (userService.existsByEmail(req.getEmail())) {
            throw new BadRequestException("Bu email artiq movcuddur");
        }

        RoleEntity buyer = roleRepo.findByName("ROLE_BUYER")
                .orElseThrow(() -> new BadRequestException("ROLE_BUYER tapilmadi"));

        UserEntity u = new UserEntity();
        u.setFullName(req.getFullName());
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));
        u.getRoles().add(buyer);

        userService.save(u);

        var ud = userService.loadUserByUsername(u.getEmail());
        String token = jwtService.generateToken(ud);

        return new AuthResponse(
                token,
                u.getId(),
                u.getEmail(),
                u.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList())
        );
    }

    public AuthResponse login(LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));

        UserEntity u = userService.findByEmail(req.getEmail());
        var ud = userService.loadUserByUsername(u.getEmail());
        String token = jwtService.generateToken(ud);

        return new AuthResponse(
                token,
                u.getId(),
                u.getEmail(),
                u.getRoles().stream().map(RoleEntity::getName).collect(Collectors.toList())
        );
    }
}