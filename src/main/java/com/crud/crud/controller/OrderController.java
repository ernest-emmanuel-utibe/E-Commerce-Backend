package com.crud.crud.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.validation.Valid;

import com.crud.crud.data.dto.OrderDto;
import com.crud.crud.data.models.Customer;
import com.crud.crud.data.models.Order;
import com.crud.crud.data.repository.OrderDao;
import com.crud.crud.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {
//    @Autowired
    private final OrderDao orderDao;

//    @Autowired
    private final OrderService orderService;

    @PostMapping("/order/place")
    public ResponseEntity<Order> addTheNewOrder(@Valid @RequestBody OrderDto odto, @RequestHeader("token") String token){

        Order savedorder = orderService.saveOrder(odto,token);
        return new ResponseEntity<Order>(savedorder,HttpStatus.CREATED);

    }

    @GetMapping("/orders")
    public List<Order> getAllOrders(){


        List<Order> listOfAllOrders = orderService.getAllOrders();
        return listOfAllOrders;

    }

    @GetMapping("/orders/{orderId}")
    public Order getOrdersByOrderId(@PathVariable("orderId") Integer orderId) {

        return orderService.getOrderByOrderId(orderId);

    }

    @DeleteMapping("/orders/{orderId}")
    public Order cancelTheOrderByOrderId(@PathVariable("orderId") Integer orderId,@RequestHeader("token") String token){

        return orderService.cancelOrderByOrderId(orderId,token);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<Order> updateOrderByOrder(@Valid @RequestBody OrderDto orderdto, @PathVariable("orderId") Integer orderId,@RequestHeader("token") String token){

        Order updatedOrder= orderService.updateOrderByOrder(orderdto,orderId,token);

        return new ResponseEntity<Order>(updatedOrder,HttpStatus.ACCEPTED);

    }

    @GetMapping("/orders/by/date")
    public List<Order> getOrdersByDate(@RequestParam("date") String date){

        DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate ld=LocalDate.parse(date,dtf);
        return orderService.getAllOrdersByDate(ld);
    }

    @GetMapping("/customer/{orderId}")
    public Customer getCustomerDetailsByOrderId(@PathVariable("orderId") Integer orderId) {
        return orderService.getCustomerByOrderid(orderId);
    }
}
