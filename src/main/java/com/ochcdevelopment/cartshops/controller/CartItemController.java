package com.ochcdevelopment.cartshops.controller;

import com.ochcdevelopment.cartshops.exceptions.ResourceNotFoundException;
import com.ochcdevelopment.cartshops.model.Cart;
import com.ochcdevelopment.cartshops.model.User;
import com.ochcdevelopment.cartshops.response.ApiResponse;
import com.ochcdevelopment.cartshops.service.cart.CartItemService;
import com.ochcdevelopment.cartshops.service.cart.CartService;
import com.ochcdevelopment.cartshops.service.cart.ICartItemService;
import com.ochcdevelopment.cartshops.service.cart.ICartService;
import com.ochcdevelopment.cartshops.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;


@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;

    //agregar items a la carta atravez de 3 parametros cartId que es de la cart,el productId viene del producto,y la cantidad que es de CartItem
    //solo en caso de cuando es agregar nada mas se utliza en los ids RequestParam luego ya cuando esta agregado es variable
    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse>addItemToCart(
                                                    @RequestParam Long productId,
                                                    @RequestParam Integer quantity){

        try {

            User user = userService.getUserById(4L);
            Cart cart = cartService.initializeNewCart(user);


            cartItemService.addItemToCart(cart.getId(),productId,quantity);
            return ResponseEntity.ok(new ApiResponse("add Item success",null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    // remover o eliminar  items de la carta atraves de dos variables
    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public  ResponseEntity<ApiResponse>removeItemFromCart(@PathVariable Long cartId,@PathVariable Long itemId){
        try {
            cartItemService.removeItemFromCart(cartId,itemId);
            return ResponseEntity.ok(new ApiResponse("Remove items Sucess", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    //actualizar la cantidad de items atravez de su cartId tambien de su itemId
    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public  ResponseEntity<ApiResponse>updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer quantity){
        try {
            cartItemService.updateItemQuantity(cartId,itemId,quantity);
            return ResponseEntity.ok(new ApiResponse("update item quantity", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/cart/{cartId}/item/{itemId}/search")
    public ResponseEntity<ApiResponse> getCartItem(@PathVariable Long cartId,
                                                   @PathVariable Long itemId){

        try {
            cartItemService.getCartItem(cartId,itemId);
            return ResponseEntity.ok(new ApiResponse("cartItem succes", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }


    }

}
