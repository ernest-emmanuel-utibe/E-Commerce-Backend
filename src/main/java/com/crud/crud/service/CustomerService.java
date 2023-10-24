package com.crud.crud.service;

import com.crud.crud.data.dto.CustomerDto;
import com.crud.crud.data.dto.CustomerUpdateDto;
import com.crud.crud.data.dto.SessionDto;
import com.crud.crud.data.models.Address;
import com.crud.crud.data.models.CreditCard;
import com.crud.crud.data.models.Customer;
import com.crud.crud.data.models.Order;
import com.crud.crud.exception.CustomerException;
import com.crud.crud.exception.CustomerNotFoundException;

import java.util.List;

public interface CustomerService {
    public Customer addCustomer(Customer customer) throws CustomerException;

    public Customer getLoggedInCustomerDetails(String token) throws CustomerNotFoundException;

    public List<Customer> getAllCustomers(String token) throws CustomerNotFoundException;

    public Customer updateCustomer(CustomerUpdateDto customer, String token) throws CustomerNotFoundException;

    public Customer updateCustomerMobileNoOrEmailId(CustomerUpdateDto customerUpdateDTO, String token) throws CustomerNotFoundException;

    public Customer updateCreditCardDetails(String token, CreditCard card) throws CustomerException;

    public SessionDto updateCustomerPassword(CustomerDto customerDTO, String token) throws CustomerNotFoundException;

    public SessionDto deleteCustomer(CustomerDto customerDTO, String token) throws CustomerNotFoundException;

    public Customer updateAddress(Address address, String type, String token) throws CustomerException;

    public Customer deleteAddress(String type, String token) throws CustomerException, CustomerNotFoundException;

    public List<Order> getCustomerOrders(String token) throws CustomerException;
}
