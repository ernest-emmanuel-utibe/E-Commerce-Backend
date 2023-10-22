package com.crud.crud.data.dto;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {
    @NotNull
    private Integer productId;

    private String productName;

    private Double price;

    @Min(1)
    private Integer quantity;

}
