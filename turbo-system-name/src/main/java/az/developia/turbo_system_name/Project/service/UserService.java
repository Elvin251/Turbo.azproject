package az.developia.turbo_system_name.Project.service;



import az.developia.turbo_system_name.Project.entity.RoleEntity;
import az.developia.turbo_system_name.Project.entity.UserEntity;
import az.developia.turbo_system_name.Project.exception.ResourceNotFoundException;
import az.developia.turbo_system_name.Project.repository.RoleRepository;
import az.developia.turbo_system_name.Project.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;

    public UserService(UserRepository userRepo, RoleRepository roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }

    public UserEntity findByEmail(String email) {
        return userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User tapilmadi"));
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    public UserEntity save(UserEntity u) {
        return userRepo.save(u);
    }

    public void becomeSeller(String email) {
        UserEntity u = findByEmail(email);
        RoleEntity seller = roleRepo.findByName("ROLE_SELLER")
                .orElseThrow(() -> new ResourceNotFoundException("ROLE_SELLER tapilmadi"));
        u.getRoles().add(seller);
        userRepo.save(u);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity u = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User tapilmadi"));

        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),
                u.getPassword(),
                u.getRoles().stream()
                        .map(r -> new SimpleGrantedAuthority(r.getName()))
                        .collect(Collectors.toList())
        );
    }
}