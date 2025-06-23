package com.ochcdevelopment.cartshops.service.cart;

import com.ochcdevelopment.cartshops.model.Cart;
import com.ochcdevelopment.cartshops.model.User;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice (Long id);

    // inicializar nueva carta
    Cart initializeNewCart(User user);

    //obtener carta por userId
    Cart getCartByUserId(Long userId);
}
