package com.crud.crud.data.repository;

import com.crud.crud.data.models.Product;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    @Query("select new com.masai.models.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity) "
            + "from Product p where p.category=:catenum")
    public List<ProductDTO> getAllProductsInACategory(@Param("catenum") CategoryEnum catenum);


    @Query("select new com.masai.models.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity) "
            + "from Product p where p.status=:status")
    public List<ProductDTO> getProductsWithStatus(@Param("status") ProductStatus status);

    @Query("select new com.masai.models.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity) "
            + "from Product p where p.seller.sellerId=:id")
    public List<ProductDTO> getProductsOfASeller(@Param("id") Integer id);
}
