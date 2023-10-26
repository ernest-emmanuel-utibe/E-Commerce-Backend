package com.crud.crud.data.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String prodName;
    private String manufacturer;
    private Double price;
    private Long quantity;
}
