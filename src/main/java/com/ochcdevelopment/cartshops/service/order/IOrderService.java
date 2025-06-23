package com.ochcdevelopment.cartshops.service.order;

import com.ochcdevelopment.cartshops.dto.OrderDto;
import com.ochcdevelopment.cartshops.model.Order;

import java.util.List;

public interface IOrderService {
    // realizar pedido medidante el userid
    Order placeOrder(Long userId);
    //recibir la order por id
    OrderDto getOrder(Long orderId);

    //listar los pepdidos que un usuario mediante su id
    List<OrderDto> getUserOrders(Long userId);

    //metodo privado solo para esta clase
    OrderDto convertToDto(Order order);
}
