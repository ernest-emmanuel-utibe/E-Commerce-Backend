package com.crud.crud.service.impl;

import com.crud.crud.service.CartItemService;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    ProductDao productDao;

    @Override
    public CartItem createItemforCart(CartDTO cartdto) {

        Product existingProduct = productDao.findById(cartdto.getProductId()).orElseThrow( () -> new ProductNotFoundException("Product Not found"));

        if(existingProduct.getStatus().equals(ProductStatus.OUTOFSTOCK) || existingProduct.getQuantity() == 0) {
            throw new ProductNotFoundException("Product OUT OF STOCK");
        }

        CartItem newItem = new CartItem();

        newItem.setCartItemQuantity(1);

        newItem.setCartProduct(existingProduct);

        return newItem;
    }
}
