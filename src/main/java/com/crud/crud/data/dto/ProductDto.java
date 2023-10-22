package com.crud.crud.data.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String prodName;
    private String manufaturer;
    private Double price;
    private Integer quantity;
}
