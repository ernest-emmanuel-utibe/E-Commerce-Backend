package com.crud.crud.controller;

import java.util.List;

import javax.validation.Valid;

import com.crud.crud.data.dto.ProductDto;
import com.crud.crud.data.models.CategoryEnum;
import com.crud.crud.data.models.Product;
import com.crud.crud.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductController {
//    @Autowired
    private final ProductService productService;

    // this method adds new product to catalog by seller(if seller is new it adds
    // seller as well
    // if seller is already existing products will be mapped to same seller) and
    // returns added product

    @PostMapping("/products")
    public ResponseEntity<Product> addProductToCatalogHandler(@RequestHeader("token") String token,
                                                              @Valid @RequestBody Product product) {

        Product prod = productService.addProductToCatalog(token, product);

        return new ResponseEntity<Product>(prod, HttpStatus.ACCEPTED);

    }

    // This method gets the product which needs to be added to the cart returns
    // product

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductFromCatalogByIdHandler(@PathVariable("id") Integer id) {

        Product prod = productService.getProductFromCatalogById(id);

        return new ResponseEntity<Product>(prod, HttpStatus.FOUND);

    }

    // This method will delete the product from catalog and returns the response
    // This will be called only when the product qty will be zero or seller wants to
    // delete for any other reason

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProductFromCatalogHandler(@PathVariable("id") Integer id) {

        String res = productService.deleteProductFromCatalog(id);
        return new ResponseEntity<String>(res, HttpStatus.OK);
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateProductInCatalogHandler(@Valid @RequestBody Product prod) {

        Product prod1 = productService.updateProductIncatalog(prod);

        return new ResponseEntity<Product>(prod1, HttpStatus.OK);

    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProductsHandler() {

        List<Product> list = productService.getAllProductsIncatalog();

        return new ResponseEntity<List<Product>>(list, HttpStatus.OK);
    }

    //this method gets the products mapped to a particular seller
    @GetMapping("/products/seller/{id}")
    public ResponseEntity<List<ProductDto>> getAllProductsOfSellerHandler(@PathVariable("id") Integer id) {

        List<ProductDto> list = productService.getAllProductsOfSeller(id);

        return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);
    }

    @GetMapping("/products/{catenum}")
    public ResponseEntity<List<ProductDto>> getAllProductsInCategory(@PathVariable("catenum") String catenum) {
        CategoryEnum ce = CategoryEnum.valueOf(catenum.toUpperCase());
        List<ProductDto> list = productService.getProductsOfCategory(ce);
        return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);

    }

    @GetMapping("/products/status/{status}")
    public ResponseEntity<List<ProductDto>> getProductsWithStatusHandler(@PathVariable("status") String status) {

        ProductStatus ps = ProductStatus.valueOf(status.toUpperCase());
        List<ProductDto> list = productService.getProductsOfStatus(ps);

        return new ResponseEntity<List<ProductDto>>(list, HttpStatus.OK);

    }


    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateQuantityOfProduct(@PathVariable("id") Integer id,@RequestBody ProductDto prodDto){

        Product prod =   productService.updateProductQuantityWithId(id, prodDto);

        return new ResponseEntity<Product>(prod,HttpStatus.ACCEPTED);
    }
}
