package com.crud.crud.controller;

import java.util.List;

import javax.validation.Valid;

import com.crud.crud.data.dto.CustomerDto;
import com.crud.crud.data.dto.CustomerUpdateDto;
import com.crud.crud.data.dto.SessionDto;
import com.crud.crud.data.models.Address;
import com.crud.crud.data.models.CreditCard;
import com.crud.crud.data.models.Customer;
import com.crud.crud.data.models.Order;
import com.crud.crud.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerController {
//    @Autowired
    private final CustomerService customerService;

    // Handler to get a list of all customers

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomersHandler(@RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.getAllCustomers(token), HttpStatus.ACCEPTED);
    }


    // Handler to Get a customer details of currently logged in user - sends data as per token

    @GetMapping("/customer/current")
    public ResponseEntity<Customer> getLoggedInCustomerDetailsHandler(@RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.getLoggedInCustomerDetails(token), HttpStatus.ACCEPTED);
    }


    // Handler to Update a customer

    @PutMapping("/customer")
    public ResponseEntity<Customer> updateCustomerHandler(@Valid @RequestBody CustomerUpdateDto customerUpdate, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.updateCustomer(customerUpdate, token), HttpStatus.ACCEPTED);
    }


    // Handler to update a customer email-id or mobile no
    @PutMapping("/customer/update/credentials")
    public ResponseEntity<Customer> updateCustomerMobileEmailHandler(@Valid @RequestBody CustomerUpdateDto customerUpdate, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.updateCustomerMobileNoOrEmailId(customerUpdate, token), HttpStatus.ACCEPTED);
    }


    // Handler to update customer password
    @PutMapping("/customer/update/password")
    public ResponseEntity<SessionDto> updateCustomerPasswordHandler(@Valid @RequestBody CustomerDto customerDto, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.updateCustomerPassword(customerDto, token), HttpStatus.ACCEPTED);
    }


    // Handler to Add or update new customer Address
    @PutMapping("/customer/update/address")
    public ResponseEntity<Customer> updateAddressHandler(@Valid @RequestBody Address address, @RequestParam("type") String type, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.updateAddress(address, type, token), HttpStatus.ACCEPTED);
    }


    // Handler to update Credit card details
    @PutMapping("/customer/update/card")
    public ResponseEntity<Customer> updateCreditCardHandler(@RequestHeader("token") String token, @Valid @RequestBody CreditCard newCard){
        return new ResponseEntity<>(customerService.updateCreditCardDetails(token, newCard), HttpStatus.ACCEPTED);
    }


    // Handler to Remove a user address
    @DeleteMapping("/customer/delete/address")
    public ResponseEntity<Customer> deleteAddressHandler(@RequestParam("type") String type, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.deleteAddress(type, token), HttpStatus.ACCEPTED);
    }

    // Handler to delete customer
    @DeleteMapping("/customer")
    public ResponseEntity<SessionDto> deleteCustomerHandler(@Valid @RequestBody CustomerDto customerDto, @RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.deleteCustomer(customerDto, token), HttpStatus.ACCEPTED);
    }



    @GetMapping("/customer/orders")
    public ResponseEntity<List<Order>> getCustomerOrdersHandler(@RequestHeader("token") String token){
        return new ResponseEntity<>(customerService.getCustomerOrders(token), HttpStatus.ACCEPTED);
    }
}
