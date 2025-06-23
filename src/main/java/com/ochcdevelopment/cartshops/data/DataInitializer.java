package com.ochcdevelopment.cartshops.data;

import com.ochcdevelopment.cartshops.model.User;
import com.ochcdevelopment.cartshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExist();
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
            user.setPassword("123456");
            userRepository.save(user);
            System.out.println("Default vet user " + i + "created successfully.");
        }
    }
}
