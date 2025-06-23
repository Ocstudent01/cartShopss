package com.ochcdevelopment.cartshops.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ochcdevelopment.cartshops.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING) //enumerador de tipo cadena
    private OrderStatus orderStatus;


    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    //esto se lee de muchos a uno muchos usuario pueder tener una orden
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
