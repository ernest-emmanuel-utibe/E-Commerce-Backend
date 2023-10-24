package com.crud.crud.data.repository;

import com.crud.crud.data.dto.ProductDto;
import com.crud.crud.data.models.CategoryEnum;
import com.crud.crud.data.models.Product;
import com.crud.crud.data.models.ProductStatus;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    @Query("select new com.crud.crud.data.dto.ProductDto(p.productName,p.manufacturer,p.price,p.quantity) "
            + "from Product p where p.category=:catenum")
    public List<ProductDto> getAllProductsInACategory(@Param("catenum") CategoryEnum catenum);


    @Query("select new com.crud.crud.data.dto.ProductDto(p.productName,p.manufacturer,p.price,p.quantity) "
            + "from Product p where p.status=:status")
    public List<ProductDto> getProductsWithStatus(@Param("status") ProductStatus status);

    @Query("select new com.crud.crud.data.dto.ProductDto(p.productName,p.manufacturer,p.price,p.quantity) "
            + "from Product p where p.seller.sellerId=:id")
    public List<ProductDto> getProductsOfASeller(@Param("id") Integer id);
}
