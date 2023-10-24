package com.crud.crud.service;

import com.crud.crud.data.dto.CartDto;
import com.crud.crud.data.models.CartItem;

public interface CartItemService {
    public CartItem createItemForCart(CartDto cartdto);
}
