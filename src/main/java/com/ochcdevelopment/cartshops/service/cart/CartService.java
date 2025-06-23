package com.ochcdevelopment.cartshops.service.cart;

import com.ochcdevelopment.cartshops.exceptions.ResourceNotFoundException;
import com.ochcdevelopment.cartshops.model.Cart;
import com.ochcdevelopment.cartshops.model.CartItem;
import com.ochcdevelopment.cartshops.model.User;
import com.ochcdevelopment.cartshops.repository.CartItemRepository;
import com.ochcdevelopment.cartshops.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class CartService implements ICartService{
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    //se usa como un incrementador en secuencia en este caso su valor va empezar en 1
    private final AtomicLong cartIdGenerator = new AtomicLong(0);

    //Guardar Carta
    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Cart not Found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        //subtract se utiliza para encontrar la diferencia aritmética de números grandes
        //cart.setTotalAmount(totalAmount.subtract(cart.getTotalAmount()));
        cart.setTotalAmount(totalAmount);
        return cartRepository.save(cart);
    }

    //Limpiar Cart
    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepository.deleteById(id);

    }

    //Precio total
    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }

    // inicializar nueva carta
    @Override
    public Cart initializeNewCart(User user){
        // ofNullable Acepta un único argumento de cualquier tipo, que puede ser un valor o nulo.
        return Optional.ofNullable(getCartByUserId(user.getId())).orElseGet(()-> {
            Cart cart = new Cart();
            cart.setUser(user);
            return  cartRepository.save(cart);
        });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }
}
