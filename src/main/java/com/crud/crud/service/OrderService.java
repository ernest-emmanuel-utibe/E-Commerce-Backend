package com.crud.crud.service;

import com.crud.crud.data.dto.OrderDto;
import com.crud.crud.data.models.Customer;
import com.crud.crud.data.models.Order;
import com.crud.crud.exception.LoginException;
import com.crud.crud.exception.OrderException;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    public Order saveOrder(OrderDto orderDto, String token) throws LoginException, OrderException;

    public Order getOrderByOrderId(Long OrderId) throws OrderException;

//    Order getOrderByOrderId(Long OrderId) throws OrderException;

    public List<Order> getAllOrders() throws OrderException;

    public Order updateOrderByOrder(OrderDto order, Long OrderId,String token) throws OrderException,LoginException;


//    Order cancelOrderByOrderId(Long OrderId, String token) throws OrderException;

//    Order updateOrderByOrder(OrderDto orderdto, Long OrderId, String token) throws OrderException,LoginException;
    public List<Order> getAllOrdersByDate(LocalDate date) throws OrderException;

    public Customer getCustomerByOrderId(Long orderId) throws OrderException;

    public Order cancelOrderByOrderId(Long OrderId,String token) throws OrderException;

//    Customer getCustomerByOrderId(Long orderId) throws OrderException;
}
