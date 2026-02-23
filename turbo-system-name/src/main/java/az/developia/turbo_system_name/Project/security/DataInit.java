package az.developia.turbo_system_name.Project.security;



import az.developia.turbo_system_name.Project.entity.RoleEntity;
import az.developia.turbo_system_name.Project.entity.UserEntity;
import az.developia.turbo_system_name.Project.repository.RoleRepository;
import az.developia.turbo_system_name.Project.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInit implements CommandLineRunner {

    private final RoleRepository roleRepo;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    public DataInit(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder encoder) {
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
        this.encoder = encoder;
    }

    @Override
    public void run(String... args) {
        RoleEntity admin = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(new RoleEntity("ROLE_ADMIN")));
        RoleEntity seller = roleRepo.findByName("ROLE_SELLER").orElseGet(() -> roleRepo.save(new RoleEntity("ROLE_SELLER")));
        RoleEntity buyer  = roleRepo.findByName("ROLE_BUYER").orElseGet(() -> roleRepo.save(new RoleEntity("ROLE_BUYER")));

        // Sistem admin user yaradır (əgər yoxdursa)
        if (!userRepo.existsByEmail("admin@turbo.az")) {
            UserEntity u = new UserEntity();
            u.setFullName("System Admin");
            u.setEmail("admin@turbo.az");
            u.setPassword(encoder.encode("Admin123"));
            u.getRoles().add(admin);
            userRepo.save(u);
        }
    }
}