package com.crud.crud.service;

import com.crud.crud.data.dto.ProductDto;
import com.crud.crud.data.models.CategoryEnum;
import com.crud.crud.data.models.Product;
import com.crud.crud.data.models.ProductStatus;

import java.util.List;

public interface ProductService {
    public Product addProductToCatalog(String token, Product product);

    public Product getProductFromCatalogById(Integer id);

    public String deleteProductFromCatalog(Integer id);

    public Product updateProductIncatalog(Product product);

    public List<Product> getAllProductsIncatalog();

    public List<ProductDto> getAllProductsOfSeller(Integer id);

    public List<ProductDto> getProductsOfCategory(CategoryEnum catenum);

    public List<ProductDto> getProductsOfStatus(ProductStatus status);

    public Product updateProductQuantityWithId(Integer id,ProductDto prodDTO);
}
