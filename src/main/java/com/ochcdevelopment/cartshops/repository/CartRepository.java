package com.ochcdevelopment.cartshops.repository;

import com.ochcdevelopment.cartshops.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long userId);
}
