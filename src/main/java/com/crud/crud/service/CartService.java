package com.crud.crud.service;

public interface CartService {
    public Cart addProductToCart(CartDTO cart, String token) throws CartItemNotFound;
    public Cart getCartProduct(String token);
    public Cart removeProductFromCart(CartDTO cartDto,String token) throws ProductNotFound;
//	public Cart changeQuantity(Product product,Customer customer,Integer quantity);

    public Cart clearCart(String token);
}
