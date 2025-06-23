package com.ochcdevelopment.cartshops.service.cart;

import com.ochcdevelopment.cartshops.exceptions.ResourceNotFoundException;
import com.ochcdevelopment.cartshops.model.Cart;
import com.ochcdevelopment.cartshops.model.CartItem;
import com.ochcdevelopment.cartshops.model.Product;
import com.ochcdevelopment.cartshops.repository.CartItemRepository;
import com.ochcdevelopment.cartshops.repository.CartRepository;
import com.ochcdevelopment.cartshops.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartItemService implements ICartItemService{

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductService productService;
    private  final CartService cartService;
    //agregar item a la carta
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1. Get the Cart
        //2. Get the product
        //3. check if the product already in the cart
        //4. If Yes, then increase the quantity with the requested quantity
        //If No, the initiate a new CartItem entry.
        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        //el total precio biene de la clase cartItem del metodo del mismo nombre setTotalPrice
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    //remover o quitar items de la carta
    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
       /* CartItem itemToRemove = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(()-> new ResourceNotFoundException("Product not found"));*/
        CartItem itemToRemove = getCartItem(cartId,productId);
        //removeItem es un metodo public de tipo void que esta ubicado en la clase Cart
        cart.removeItem(itemToRemove);
        //guardar en el repositorio
        cartRepository.save(cart);
    }

    //actualizar la cantidad de los items
    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
        Cart cart = cartService.getCart(cartId);
        cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item ->{
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    //setTotalPrice es un metodo publico de la clase CartItem y porque set xq ya esta haciendo dicha operacion
                    item.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getItems()
                .stream().map(CartItem ::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    //buscar el item de la carta atravez de su cartId, productId
    @Override
    public CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId);
        return cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElseThrow(()->new ResourceNotFoundException("Item not found"));
    }



}
