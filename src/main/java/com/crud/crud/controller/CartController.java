package com.crud.crud.controller;

import java.util.List;

import com.crud.crud.data.dto.CartDto;
import com.crud.crud.data.models.Cart;
import com.crud.crud.data.repository.CartDao;
import com.crud.crud.data.repository.CustomerDao;
import com.crud.crud.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CartController {
//    @Autowired
    private final CartService cartService;

//    @Autowired
    private final CartDao cartDao;

//    @Autowired
    private final CustomerDao customerDao;


    @PostMapping(value = "/cart/add")
    public ResponseEntity<Cart> addProductToCartHander(@RequestBody CartDto cartdto , @RequestHeader("token")String token){

        Cart cart = cartService.addProductToCart(cartdto, token);
        return new ResponseEntity<Cart>(cart,HttpStatus.CREATED);
    }

    //
    @GetMapping(value = "/cart")
    public ResponseEntity<Cart> getCartProductHandler(@RequestHeader("token")String token){
        return new ResponseEntity<>(cartService.getCartProduct(token), HttpStatus.ACCEPTED);
    }


    @DeleteMapping(value = "/cart")
    public ResponseEntity<Cart> removeProductFromCartHander(@RequestBody CartDto cartdto ,@RequestHeader("token")String token){

        Cart cart = cartService.removeProductFromCart(cartdto, token);
        return new ResponseEntity<Cart>(cart,HttpStatus.OK);
    }


    @DeleteMapping(value = "/cart/clear")
    public ResponseEntity<Cart> clearCartHandler(@RequestHeader("token") String token){
        return new ResponseEntity<>(cartService.clearCart(token), HttpStatus.ACCEPTED);
    }

}
