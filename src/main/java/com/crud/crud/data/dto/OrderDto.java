package com.crud.crud.data.dto;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

//import com.crud.crud.data.models.CreditCard;
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
}
