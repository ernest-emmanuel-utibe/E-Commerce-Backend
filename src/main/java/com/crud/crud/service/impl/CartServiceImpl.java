package com.crud.crud.service.impl;

import com.crud.crud.data.dto.CartDto;
import com.crud.crud.data.models.Cart;
import com.crud.crud.data.models.CartItem;
import com.crud.crud.data.models.Customer;
import com.crud.crud.data.models.UserSession;
import com.crud.crud.data.repository.CartDao;
import com.crud.crud.data.repository.CustomerDao;
import com.crud.crud.data.repository.ProductDao;
import com.crud.crud.data.repository.SessionDao;
import com.crud.crud.exception.CartItemNotFoundException;
import com.crud.crud.exception.CustomerNotFoundException;
import com.crud.crud.exception.LoginException;
import com.crud.crud.exception.ProductNotFoundException;
import com.crud.crud.service.CartItemService;
import com.crud.crud.service.CartService;
import com.crud.crud.service.LoginLogoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {
//    @Autowired
    private final CartDao cartDao;

//    @Autowired
    private final SessionDao sessionDao;

//    @Autowired
    private final CartItemService cartItemService;


//    @Autowired
    private final CustomerDao customerDao;

//    @Autowired
    private final LoginLogoutService loginService;


//    @Autowired
    private final ProductDao productDao;


    @Override
    public Cart addProductToCart(CartDto cartDto, String token) throws CartItemNotFoundException {
        if(token.contains("customer") == false) {
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Optional<Customer> customer = customerDao.findById(user.getUserId());

        if(customer.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = customer.get();

        Cart customerCart = existingCustomer.getCustomerCart();

        List<CartItem> cartItems = customerCart.getCartItems();

        CartItem item = cartItemService.createItemForCart(cartDto);


        if(cartItems.isEmpty()) {
            cartItems.add(item);
            customerCart.setCartTotal(item.getCartProduct().getPrice());
        }
        else {
            boolean flag = false;
            for(CartItem c: cartItems) {
                if(c.getCartProduct().getProductId() == cartDto.getProductId()) {
                    c.setCartItemQuantity(c.getCartItemQuantity() + 1);
                    customerCart.setCartTotal(customerCart.getCartTotal() + c.getCartProduct().getPrice());
                    flag = true;
                }
            }
            if(!flag) {
                cartItems.add(item);
                customerCart.setCartTotal(customerCart.getCartTotal() + item.getCartProduct().getPrice());
            }
        }

        return cartDao.save(existingCustomer.getCustomerCart());
    }

    @Override
    public Cart getCartProduct(String token) {

        System.out.println("Inside get cart");

        if(!token.contains("customer")) {
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Optional<Customer> customer = customerDao.findById(user.getUserId());


        if(customer.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = customer.get();

//
        Long cartId = existingCustomer.getCustomerCart().getCartId();


        Optional<Cart> optionalCart= cartDao.findById(cartId);

        if(optionalCart.isEmpty()) {
            throw new CartItemNotFoundException("cart Not found by Id");
        }
//		return optionalCart.get().getProducts();

        return optionalCart.get();
//		return cart.getProducts();
    }

    @Override
    public Cart removeProductFromCart(CartDto cartDto, String token) throws ProductNotFoundException {
        if(!token.contains("customer")) {
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Optional<Customer> opt = customerDao.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        Cart customerCart = existingCustomer.getCustomerCart();

        List<CartItem> cartItems = customerCart.getCartItems();

        if(cartItems.isEmpty()) {
            throw new CartItemNotFoundException("Cart is empty");
        }


        boolean flag = false;

        for(CartItem cartItem: cartItems) {
            System.out.println("Item" + cartItem.getCartProduct());
            if(cartItem.getCartProduct().getProductId() == cartDto.getProductId()) {
                cartItem.setCartItemQuantity(cartItem.getCartItemQuantity() - 1);

                customerCart.setCartTotal(customerCart.getCartTotal() - cartItem.getCartProduct().getPrice());
                if(cartItem.getCartItemQuantity() == 0) {

                    cartItems.remove(cartItem);


                    return cartDao.save(customerCart);
                }
                flag = true;
            }
        }

        if(!flag) {
            throw new CartItemNotFoundException("Product not added to cart");
        }

        if(cartItems.size() == 0) {
            cartDao.save(customerCart);
            throw new CartItemNotFoundException("Cart is empty now");
        }

        return cartDao.save(customerCart);
    }




    // Method to clear entire cart

    @Override
    public Cart clearCart(String token) {

        if(!token.contains("customer")) {
            throw new LoginException("Invalid session token for customer");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Optional<Customer> opt = customerDao.findById(user.getUserId());

        if(opt.isEmpty())
            throw new CustomerNotFoundException("Customer does not exist");

        Customer existingCustomer = opt.get();

        Cart customerCart = existingCustomer.getCustomerCart();

        if(customerCart.getCartItems().isEmpty()) {
            throw new CartItemNotFoundException("Cart already empty");
        }

        List<CartItem> emptyCart = new ArrayList<>();

        customerCart.setCartItems(emptyCart);

        customerCart.setCartTotal(0.0);

        return cartDao.save(customerCart);
    }
}
