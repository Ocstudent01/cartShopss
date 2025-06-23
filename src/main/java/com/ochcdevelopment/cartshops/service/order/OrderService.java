package com.ochcdevelopment.cartshops.service.order;

import com.ochcdevelopment.cartshops.dto.OrderDto;
import com.ochcdevelopment.cartshops.enums.OrderStatus;
import com.ochcdevelopment.cartshops.exceptions.ResourceNotFoundException;
import com.ochcdevelopment.cartshops.model.*;
import com.ochcdevelopment.cartshops.repository.OrderRepository;
import com.ochcdevelopment.cartshops.repository.ProductRepository;
import com.ochcdevelopment.cartshops.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService{


    private final OrderRepository orderRepository;
    private final ProductRepository  productRepository;

    //aqui es para mostrar toda la data que contiene esta clase en este caso CartService
    private final CartService cartService;

    //se usa para el mapeo entre objetos esto lo hace que se optimise
    private final ModelMapper modelMapper;


    //realizar
    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        //aqui captura en el objeto cart, la carta que el usuario pide mediante su id
        Cart cart = cartService.getCartByUserId(userId);

        //aqui muestro mis metodos privados que cree en la parte de abajo
        //capturo la carta creada
        Order order = createOrder(cart);

        //listo los items de la orden
        List<OrderItem> orderItemList = createOrderItems(order, cart);

        //para mostrar un listadoi tienes que hacerv uso de hashSet
        //muestro los items de la orden y me va mostrar un listado de ellos
        order.setOrderItems(new HashSet<>(orderItemList));

        //mostrar el monto total que ya calcule en el metodo privado llamado calculateTotalAmount que esta en la parte de abajo
        order.setTotalAmount(calculateTotalAmount(orderItemList));

        // aqui va guardar el pedido en el repositorio y capturado en un objeto de la clase order llamado savedOrder
        Order savedOrder = orderRepository.save(order);

        //aqui se va limpiar la carta mediante su id
        cartService.clearCart(cart.getId());

        return savedOrder;
    }

    //crear orden pero de la carta
    private Order createOrder(Cart cart){
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        return order;
    }

    //crear el listado de order items
    private List<OrderItem> createOrderItems(Order order, Cart cart){
        return cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            //aqui muestra los productos del inventario  segun la cantidad que se tiene y segun la cantidad que pidas va ir restando
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);

            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
    }

    //metodo privado solo para esta clase
    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList){
        return orderItemList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }

    //recibir la orden mediante su id
    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDto)
                .orElseThrow(()-> new ResourceNotFoundException("Order not found"));
    }

    //listar los pedidos de un usuario mediante su userId
    @Override
    public List<OrderDto> getUserOrders(Long userId){
        List<Order> orders = orderRepository.findByUserId(userId);

        return orders.stream().map(this::convertToDto).toList();
    }

    //metodo privado solo para esta clase
    @Override
    public OrderDto convertToDto(Order order){
        return  modelMapper.map(order,OrderDto.class);
    }
}
