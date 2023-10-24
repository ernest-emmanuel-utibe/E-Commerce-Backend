package com.crud.crud.service;

import com.crud.crud.data.dto.OrderDto;
import com.crud.crud.data.models.Customer;
import com.crud.crud.data.models.Order;
import com.crud.crud.exception.LoginException;
import com.crud.crud.exception.OrderException;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    public Order saveOrder(OrderDto odto, String token) throws LoginException, OrderException;

    public Order getOrderByOrderId(Integer OrderId) throws OrderException;

    public List<Order> getAllOrders() throws OrderException;

    public Order cancelOrderByOrderId(Integer OrderId,String token) throws OrderException;

    public Order updateOrderByOrder(OrderDto order,Integer OrderId,String token) throws OrderException,LoginException;

    public List<Order> getAllOrdersByDate(LocalDate date) throws OrderException;

    public Customer getCustomerByOrderid(Integer orderId) throws OrderException;
}
