package com.ochcdevelopment.cartshops.data;

import com.ochcdevelopment.cartshops.model.Role;
import com.ochcdevelopment.cartshops.model.User;
import com.ochcdevelopment.cartshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_CUSTOMER");
            createDefaultUserIfNotExist();
            createDefaultRoleIfNotExits(defaultRoles);
    }

    private void createDefaultUserIfNotExist(){
        for (int i = 1;i <= 5; i++){
            String defaultEmail = "user"+i+"@email.com";
            if(userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstname("The user");
            user.setLastname("User"+ i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("password"));
            userRepository.save(user);
            System.out.println("Default vet user " + i + "created successfully.");
        }
    }

    private void createDefaultRoleIfNotExits(Set<String> roles){
        roles.stream().filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new).forEach(roleRepository::save);
    }
}
