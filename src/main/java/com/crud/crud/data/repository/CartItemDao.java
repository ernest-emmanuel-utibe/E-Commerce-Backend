package com.crud.crud.data.repository;

import com.crud.crud.data.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemDao extends JpaRepository<Cart, Long> {
}
