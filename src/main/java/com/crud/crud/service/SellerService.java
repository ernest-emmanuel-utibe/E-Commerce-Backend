package com.crud.crud.service;

import com.crud.crud.data.dto.SellerDto;
import com.crud.crud.data.dto.SessionDto;
import com.crud.crud.data.models.Seller;
import com.crud.crud.exception.SellerException;

import java.util.List;

public interface SellerService {
    public Seller addSeller(Seller seller);

    public List<Seller> getAllSellers() throws SellerException;

    public Seller getSellerById(Integer sellerId)throws SellerException;

    public Seller getSellerByMobile(String mobile, String token) throws SellerException;

    public Seller getCurrentlyLoggedInSeller(String token) throws SellerException;

    public SessionDto updateSellerPassword(SellerDto sellerDTO, String token) throws SellerException;

    public Seller updateSeller(Seller seller, String token)throws SellerException;

    public Seller updateSellerMobile(SellerDto sellerdto, String token)throws SellerException;

    public Seller deleteSellerById(Integer sellerId, String token)throws SellerException;
}
