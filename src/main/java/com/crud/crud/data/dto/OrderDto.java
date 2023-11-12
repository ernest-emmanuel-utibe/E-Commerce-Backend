package com.crud.crud.data.dto;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

//import com.crud.crud.data.models.CreditCard;
import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderDto {
    @NotNull
    @Embedded
    private CreditCardNumber cardNumber;

    @NotNull
    private String addressType;

    @Pattern(regexp = "[0-9]{2}/[0-9]{2,4}", message = "Invalid validity. Enter in MM/YY or MM/YYYY format")
    private String cardValidity;

    @Pattern(regexp = "[0-9]{3}", message = "Invalid CVV. Must be numeric 3 digits length.")
    private String cardCVV;
}
