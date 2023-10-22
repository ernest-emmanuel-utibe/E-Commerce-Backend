package com.crud.crud.service;

public interface LoginLogoutService {
    public UserSession loginCustomer(CustomerDTO customer);

    public SessionDTO logoutCustomer(SessionDTO session);

    public void checkTokenStatus(String token);

    public void deleteExpiredTokens();


    public UserSession loginSeller(SellerDTO seller);

    public SessionDTO logoutSeller(SessionDTO session);
}
