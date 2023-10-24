package com.crud.crud.service;

import com.crud.crud.data.dto.CustomerDto;
import com.crud.crud.data.dto.SellerDto;
import com.crud.crud.data.dto.SessionDto;
import com.crud.crud.data.models.UserSession;

public interface LoginLogoutService {
    public UserSession loginCustomer(CustomerDto customer);

    public SessionDto logoutCustomer(SessionDto session);

    public void checkTokenStatus(String token);

    public void deleteExpiredTokens();


    public UserSession loginSeller(SellerDto seller);

    public SessionDto logoutSeller(SessionDto session);
}
