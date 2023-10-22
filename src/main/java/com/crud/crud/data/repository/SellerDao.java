package com.crud.crud.data.repository;

import com.crud.crud.data.models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerDao extends JpaRepository<Seller, Long> {
    Optional<Seller> findByMobile(String mobile);
}
