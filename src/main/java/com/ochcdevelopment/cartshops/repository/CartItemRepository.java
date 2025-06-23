package com.ochcdevelopment.cartshops.repository;

import com.ochcdevelopment.cartshops.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
}
