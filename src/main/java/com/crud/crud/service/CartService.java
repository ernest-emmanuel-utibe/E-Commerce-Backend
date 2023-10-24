package com.crud.crud.service;

import com.crud.crud.data.dto.CartDto;
import com.crud.crud.data.models.Cart;
import com.crud.crud.exception.CartItemNotFoundException;
import com.crud.crud.exception.ProductNotFoundException;

public interface CartService {
    public Cart addProductToCart(CartDto cart, String token) throws CartItemNotFoundException;
    public Cart getCartProduct(String token);
    public Cart removeProductFromCart(CartDto cartDto,String token) throws ProductNotFoundException;
    public Cart clearCart(String token);
}
