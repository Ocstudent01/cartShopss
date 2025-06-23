package com.ochcdevelopment.cartshops.request;

import com.ochcdevelopment.cartshops.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data //esto solo funciona con atributos que no tengan getters and setters
public class AddProductRequest {
    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;
}
