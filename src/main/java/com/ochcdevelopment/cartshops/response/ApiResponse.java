package com.ochcdevelopment.cartshops.response;


import lombok.AllArgsConstructor;
import lombok.Data;

//me permite ya no crear constror a mis atributos asi como tambien sus argumentos
@AllArgsConstructor
@Data
public class ApiResponse {
    private String message;
    private Object data;
}
