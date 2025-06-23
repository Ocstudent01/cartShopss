package com.ochcdevelopment.cartshops.service.cart;

import com.ochcdevelopment.cartshops.model.CartItem;

public interface ICartItemService  {

    //agregar el item a la carta
    void addItemToCart( Long cartId,Long productId, int quantity);
    //remover o eliminar el item de la carta
    void removeItemFromCart(Long cartId, Long productId);
    //actualizar la cantidad de items de la carta
    void updateItemQuantity(Long cartId, Long productId, int quantity);

    CartItem getCartItem(Long cartId, Long productId);
}
