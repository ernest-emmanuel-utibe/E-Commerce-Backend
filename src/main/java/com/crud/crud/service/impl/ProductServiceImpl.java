package com.crud.crud.service.impl;

import com.crud.crud.data.dto.ProductDto;
import com.crud.crud.data.models.CategoryEnum;
import com.crud.crud.data.models.Product;
import com.crud.crud.data.models.ProductStatus;
import com.crud.crud.data.models.Seller;
import com.crud.crud.data.repository.ProductDao;
import com.crud.crud.data.repository.SellerDao;
import com.crud.crud.exception.CategoryNotFoundException;
import com.crud.crud.exception.ProductNotFoundException;
import com.crud.crud.service.ProductService;
import com.crud.crud.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
//    @Autowired
    private final ProductDao productDao;

//    @Autowired
    private final SellerService sellerService;

//    @Autowired
    private final SellerDao sellerDao;

    @Override
    public Product addProductToCatalog(String token, Product product) {

        Product prod = null;
        Seller seller1 = sellerService.getCurrentlyLoggedInSeller(token);
        product.setSeller(seller1);

        Seller Existingseller = sellerService.getSellerByMobile(product.getSeller().getMobile(), token);
        Optional<Seller> opt = sellerDao.findById(Existingseller.getSellerId());

        if (opt.isPresent()) {
            Seller seller = opt.get();

            product.setSeller(seller);

            prod = productDao.save(product);
            ;

            seller.getProduct().add(product);
            sellerDao.save(seller);

        } else {
            prod = productDao.save(product);
            ;
        }

        return prod;
    }

    @Override
    public Product getProductFromCatalogById(Integer id) throws ProductNotFoundException {

        Optional<Product> opt = productDao.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }

        else
            throw new ProductNotFoundException("Product not found with given id");
    }

    @Override
    public String deleteProductFromCatalog(Integer id) throws ProductNotFoundException {
        Optional<Product> opt = productDao.findById(id);

        if (opt.isPresent()) {
            Product prod = opt.get();
            System.out.println(prod);
            productDao.delete(prod);
            return "Product deleted from catalog";
        } else
            throw new ProductNotFoundException("Product not found with given id");

    }

    @Override
    public Product updateProductIncatalog(Product prod) throws ProductNotFoundException {

        Optional<Product> opt = productDao.findById(prod.getProductId());

        if (opt.isPresent()) {
            opt.get();
            Product prod1 = productDao.save(prod);
            return prod1;
        } else
            throw new ProductNotFoundException("Product not found with given id");
    }

    @Override
    public List<Product> getAllProductsIncatalog() {
        List<Product> list = productDao.findAll();

        if (list.size() > 0) {
            return list;
        } else
            throw new ProductNotFoundException("No products in catalog");

    }

    @Override
    public List<ProductDto> getProductsOfCategory(CategoryEnum catenum) {

        List<ProductDto> list = productDao.getAllProductsInACategory(catenum);
        if (list.size() > 0) {

            return list;
        } else
            throw new CategoryNotFoundException("No products found with category:" + catenum);
    }

    @Override
    public List<ProductDto> getProductsOfStatus(ProductStatus status) {

        List<ProductDto> list = productDao.getProductsWithStatus(status);

        if (list.size() > 0) {
            return list;
        } else
            throw new ProductNotFoundException("No products found with given status:" + status);
    }

    @Override
    public Product updateProductQuantityWithId(Integer id, ProductDto prodDto) {
        Product prod = null;
        Optional<Product> opt = productDao.findById(id);

        if(opt!=null) {
            prod = opt.get();
            prod.setQuantity(prod.getQuantity()+prodDto.getQuantity());
            if(prod.getQuantity()>0) {
                prod.setStatus(ProductStatus.AVAILABLE);
            }
            productDao.save(prod);

        }
        else
            throw new ProductNotFoundException("No product found with this Id");

        return prod;
    }



    @Override
    public List<ProductDto> getAllProductsOfSeller(Integer id) {

        List<ProductDto> list = productDao.getProductsOfASeller(id);

        if(list.size()>0) {

            return list;

        }

        else {
            throw new ProductNotFoundException("No products with SellerId: "+id);
        }
    }
}
