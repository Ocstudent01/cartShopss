package com.ochcdevelopment.cartshops.data;

import com.ochcdevelopment.cartshops.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role, Long> {

    CharSequence findByName(String role);
}
