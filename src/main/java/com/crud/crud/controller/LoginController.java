package com.crud.crud.controller;

import javax.validation.Valid;

import com.crud.crud.data.dto.CustomerDto;
import com.crud.crud.data.dto.SellerDto;
import com.crud.crud.data.dto.SessionDto;
import com.crud.crud.data.models.Customer;
import com.crud.crud.data.models.Seller;
import com.crud.crud.data.models.UserSession;
import com.crud.crud.service.CustomerService;
import com.crud.crud.service.LoginLogoutService;
import com.crud.crud.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
//    @Autowired
    private final CustomerService customerService;

//    @Autowired
    private final LoginLogoutService loginService;

//    @Autowired
    private final SellerService sellerService;


    // Handler to register a new customer

    @PostMapping(value = "/register/customer", consumes = "application/json")
    public ResponseEntity<Customer> registerAccountHandler(@Valid @RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.addCustomer(customer), HttpStatus.CREATED);
    }

    // Handler to login a user

    @PostMapping(value = "/login/customer", consumes = "application/json")
    public ResponseEntity<UserSession> loginCustomerHandler(@Valid @RequestBody CustomerDto customerdto){
        return new ResponseEntity<>(loginService.loginCustomer(customerdto), HttpStatus.ACCEPTED);
    }


    // Handler to logout a user

    @PostMapping(value = "/logout/customer", consumes = "application/json")
    public ResponseEntity<SessionDto> logoutCustomerHandler(@RequestBody SessionDto sessionToken){
        return new ResponseEntity<>(loginService.logoutCustomer(sessionToken), HttpStatus.ACCEPTED);
    }




    /*********** SELLER REGISTER LOGIN LOGOUT HANDLER ************/

    @PostMapping(value = "/register/seller", consumes = "application/json")
    public ResponseEntity<Seller> registerSellerAccountHandler(@Valid @RequestBody Seller seller) {
        return new ResponseEntity<>(sellerService.addSeller(seller), HttpStatus.CREATED);
    }


    // Handler to login a user

    @PostMapping(value = "/login/seller", consumes = "application/json")
    public ResponseEntity<UserSession> loginSellerHandler(@Valid @RequestBody SellerDto seller){
        return new ResponseEntity<>(loginService.loginSeller(seller), HttpStatus.ACCEPTED);
    }


    // Handler to logout a user

    @PostMapping(value = "/logout/seller", consumes = "application/json")
    public ResponseEntity<SessionDto> logoutSellerHandler(@RequestBody SessionDto sessionToken){
        return new ResponseEntity<>(loginService.logoutSeller(sessionToken), HttpStatus.ACCEPTED);
    }
}
