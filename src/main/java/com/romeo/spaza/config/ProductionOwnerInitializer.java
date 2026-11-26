package com.romeo.spaza.config;

import com.romeo.spaza.model.Role;
import com.romeo.spaza.model.User;
import com.romeo.spaza.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Profile("production")
public class ProductionOwnerInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String username;
    private final String password;
    private final String fullName;

    public ProductionOwnerInitializer(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.bootstrap.owner.username}") String username,
            @Value("${app.bootstrap.owner.password}") String password,
            @Value("${app.bootstrap.owner.full-name:Shop Owner}") String fullName
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    @Override
    public void run(String... args) {
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password) || password.length() < 12) {
            throw new IllegalStateException("Production owner credentials are missing or the password is too short");
        }

        userRepository.findByUsername(username).ifPresentOrElse(user -> {
            if (user.getRole() != Role.ROLE_OWNER) {
                throw new IllegalStateException("Configured bootstrap account exists but is not an owner");
            }
        }, () -> userRepository.save(User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .fullName(fullName)
                .role(Role.ROLE_OWNER)
                .build()));
    }
}
