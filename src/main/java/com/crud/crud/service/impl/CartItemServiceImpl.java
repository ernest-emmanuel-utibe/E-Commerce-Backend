package com.crud.crud.service.impl;

import com.crud.crud.data.dto.CartDto;
import com.crud.crud.data.models.CartItem;
import com.crud.crud.data.models.Product;
import com.crud.crud.data.models.ProductStatus;
import com.crud.crud.data.repository.ProductDao;
import com.crud.crud.exception.ProductNotFoundException;
import com.crud.crud.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final ProductDao productDao;

    @Override
    public CartItem createItemForCart(CartDto cartdto) {
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
