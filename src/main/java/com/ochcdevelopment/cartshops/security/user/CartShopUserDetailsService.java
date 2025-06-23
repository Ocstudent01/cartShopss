package com.ochcdevelopment.cartshops.security.user;

import com.ochcdevelopment.cartshops.model.User;
import com.ochcdevelopment.cartshops.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartShopUserDetailsService implements UserDetailsService {

    private static UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
        return CartShopUserDetails.buildUserDetails(user);
    }

}
