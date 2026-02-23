package az.developia.turbo_system_name.Project.security;




import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtFilter;

    public SecurityConfig(JwtAuthFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain chain(HttpSecurity http) throws Exception {
        http.csrf(c -> c.disable());
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/**/*.html", "/**/*.css", "/**/*.js").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/ads/active").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/ads/{id}").permitAll()

                .requestMatchers("/api/brands/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/models/**").hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/ads").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/ads/*/status").hasAuthority("ROLE_ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/ads").hasAuthority("ROLE_SELLER")
                .requestMatchers(HttpMethod.PUT, "/api/ads/*").hasAuthority("ROLE_SELLER")
                .requestMatchers(HttpMethod.GET, "/api/ads/mine").hasAuthority("ROLE_SELLER")
                .requestMatchers(HttpMethod.DELETE, "/api/ads/*").hasAnyAuthority("ROLE_SELLER", "ROLE_ADMIN")

                .requestMatchers("/api/favorites/**").hasAuthority("ROLE_BUYER")

                .requestMatchers(HttpMethod.PUT, "/api/users/become-seller").hasAuthority("ROLE_BUYER")

                .anyRequest().authenticated()
        );

        http.headers(h -> h.frameOptions(f -> f.disable())); // h2 console üçün
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration cfg) throws Exception {
        return cfg.getAuthenticationManager();
    }
}