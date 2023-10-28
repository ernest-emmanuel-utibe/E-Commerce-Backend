package com.crud.crud.service.impl;

import com.crud.crud.data.dto.CartDto;
import com.crud.crud.data.dto.OrderDto;
import com.crud.crud.data.models.*;
import com.crud.crud.data.repository.OrderDao;
import com.crud.crud.exception.LoginException;
import com.crud.crud.exception.OrderException;
import com.crud.crud.service.CustomerService;
import com.crud.crud.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
//    @Autowired
    private final OrderDao orderDao;

//    @Autowired
    private final CustomerService customerService;

//    @Autowired
    private final CartServiceImpl cartService;


    @Override
    public Order saveOrder(OrderDto orderDto, String token) throws LoginException, OrderException {

        Order newOrder= new Order();

        Customer loggedInCustomer= customerService.getLoggedInCustomerDetails(token);

        if(loggedInCustomer != null) {
            //Customer loggedInCustomer= cs.getLoggedInCustomerDetails(token);
            newOrder.setCustomer(loggedInCustomer);
            String usersCardNumber= loggedInCustomer.getCreditCard().getCardNumber();
            String userGivenCardNumber= String.valueOf(orderDto.getCardNumber());
            List<CartItem> productsInCart= loggedInCustomer.getCustomerCart().getCartItems();
            List<CartItem> productsInOrder = new ArrayList<>(productsInCart);

            newOrder.setOrderCartItems(productsInOrder);
            newOrder.setTotal(loggedInCustomer.getCustomerCart().getCartTotal());



            if(!productsInCart.isEmpty()) {
                if((usersCardNumber.equals(userGivenCardNumber))
                        && (orderDto.getCardNumber().getCardValidity().equals(loggedInCustomer.getCreditCard().getCardValidity())
                        && (orderDto.getCardNumber().getCardCVV().equals(loggedInCustomer.getCreditCard().getCardCVV())))) {

                    newOrder.setCardNumber(String.valueOf(orderDto.getCardNumber()));
                    newOrder.setAddress(loggedInCustomer.getAddress().get(orderDto.getAddressType()));
                    newOrder.setDate(LocalDate.now());
                    newOrder.setOrderStatus(OrderStatusValue.SUCCESS);
                    System.out.println(usersCardNumber);
                    List<CartItem> cartItemsList= loggedInCustomer.getCustomerCart().getCartItems();

                    for(CartItem cartItem : cartItemsList ) {
                        long remainingQuantity = (long) (cartItem.getCartProduct().getQuantity()-cartItem.getCartItemQuantity());
                        if(remainingQuantity < 0 || cartItem.getCartProduct().getStatus() == ProductStatus.OUTOFSTOCK) {
                            CartDto cartdto = new CartDto();
                            cartdto.setProductId(cartItem.getCartProduct().getProductId());
                            cartService.removeProductFromCart(cartdto, token);
                            throw new OrderException("Product "+ cartItem.getCartProduct().getProductName() + " OUT OF STOCK");
                        }
                        cartItem.getCartProduct().setQuantity(remainingQuantity);
                        if(cartItem.getCartProduct().getQuantity()==0) {
                            cartItem.getCartProduct().setStatus(ProductStatus.OUTOFSTOCK);
                        }
                    }
                    cartService.clearCart(token);
                    return orderDao.save(newOrder);
                }
                else {
                    System.out.println("Not same");
                    newOrder.setCardNumber(null);
                    newOrder.setAddress(loggedInCustomer.getAddress().get(orderDto.getAddressType()));
                    newOrder.setDate(LocalDate.now());
                    newOrder.setOrderStatus(OrderStatusValue.PENDING);
                    cartService.clearCart(token);
                    return orderDao.save(newOrder);

                }
            }
            else {
                throw new OrderException("No products in Cart");
            }
        }
        else {
            throw new LoginException("Invalid session token for customer"+"Kindly Login");
        }
    }

    @Override
    public Order getOrderByOrderId(Long OrderId) throws OrderException {
        return orderDao.findById(OrderId).orElseThrow(()-> new OrderException("No order exists with given OrderId "+ OrderId));
    }

    @Override
    public List<Order> getAllOrders() throws OrderException {
        // TODO Auto-generated method stub

        List<Order> orders = orderDao.findAll();
        if(!orders.isEmpty())
            return orders;
        else
            throw new OrderException("No Orders exists on your account");
    }

    @Override
    public Order updateOrderByOrder(OrderDto orderDto, Long OrderId, String token) throws OrderException, LoginException {
        Order existingOrder = orderDao.findById(OrderId).orElseThrow(()->new OrderException("No order exists with given OrderId "+ OrderId));

        if(Objects.equals(existingOrder.getCustomer().getCustomerId(), customerService.getLoggedInCustomerDetails(token).getCustomerId())) {
            //existingOrder.setCardNumber(ordered.getCardNumber().getCardNumber());
            //existingOrder.setAddress(existingOrder.getCustomer().getAddress().get(ordered.getAddressType()));
            Customer loggedInCustomer = customerService.getLoggedInCustomerDetails(token);
            String usersCardNumber= loggedInCustomer.getCreditCard().getCardNumber();
            String userGivenCardNumber= String.valueOf(orderDto.getCardNumber());
            if((usersCardNumber.equals(userGivenCardNumber))
                    && (orderDto.getCardNumber().getCardValidity().equals(loggedInCustomer.getCreditCard().getCardValidity())
                    && (orderDto.getCardNumber().getCardCVV().equals(loggedInCustomer.getCreditCard().getCardCVV())))) {
                existingOrder.setCardNumber(String.valueOf(orderDto.getCardNumber()));
                existingOrder.setAddress(existingOrder.getCustomer().getAddress().get(orderDto.getAddressType()));
                existingOrder.setOrderStatus(OrderStatusValue.SUCCESS);
                List<CartItem> cartItemsList= existingOrder.getOrderCartItems();
                for(CartItem cartItem : cartItemsList ) {
                    long remainingQuantity = cartItem.getCartProduct().getQuantity()-cartItem.getCartItemQuantity();
                    if(remainingQuantity < 0 || cartItem.getCartProduct().getStatus() == ProductStatus.OUTOFSTOCK) {
                        CartDto cartdto = new CartDto();
                        cartdto.setProductId(cartItem.getCartProduct().getProductId());
                        cartService.removeProductFromCart(cartdto, token);
                        throw new OrderException("Product "+ cartItem.getCartProduct().getProductName() + " OUT OF STOCK");
                    }
                    cartItem.getCartProduct().setQuantity(remainingQuantity);
                    if(cartItem.getCartProduct().getQuantity()==0) {
                        cartItem.getCartProduct().setStatus(ProductStatus.OUTOFSTOCK);
                    }
                }
                return orderDao.save(existingOrder);
            }
            else {
                throw new OrderException("Incorrect Card Number Again" + usersCardNumber + userGivenCardNumber);
            }


        }
        else {
            throw new LoginException("Invalid session token for customer"+"Kindly Login");
        }
    }

    @Override
    public List<Order> getAllOrdersByDate(LocalDate date) throws OrderException {

        return orderDao.findByDate(date);
    }

    @Override
    public Customer getCustomerByOrderId(Long orderId) throws OrderException {
        Optional<Order> opt= orderDao.findById(orderId);
        if(opt.isPresent()) {
            Order existingorder= opt.get();

            return orderDao.getCustomerByOrderId(existingorder.getCustomer().getCustomerId());
        }
        else
            throw new OrderException("No Order exists with orderId "+orderId);
    }

    @Override
    public Order cancelOrderByOrderId(Long OrderId, String token) throws OrderException {
        Order order= orderDao.findById(OrderId).orElseThrow(()->new OrderException("No order exists with given OrderId "+ OrderId));
        if(Objects.equals(order.getCustomer().getCustomerId(), customerService.getLoggedInCustomerDetails(token).getCustomerId())) {
            if(order.getOrderStatus()==OrderStatusValue.PENDING) {
                order.setOrderStatus(OrderStatusValue.CANCELLED);
                orderDao.save(order);
                return order;
            }
            else if(order.getOrderStatus()==OrderStatusValue.SUCCESS) {
                order.setOrderStatus(OrderStatusValue.CANCELLED);
                List<CartItem> cartItemsList= order.getOrderCartItems();

                for(CartItem cartItem : cartItemsList ) {
                    Long addedQuantity = cartItem.getCartProduct().getQuantity()+cartItem.getCartItemQuantity();
                    cartItem.getCartProduct().setQuantity(addedQuantity);
                    if(cartItem.getCartProduct().getStatus() == ProductStatus.OUTOFSTOCK) {
                        cartItem.getCartProduct().setStatus(ProductStatus.AVAILABLE);
                    }
                }

                orderDao.save(order);
                return order;
            }
            else {
                throw new OrderException("Order was already cancelled");
            }
        }
        else {
            throw new LoginException("Invalid session token for customer"+"Kindly Login");
        }
    }
}