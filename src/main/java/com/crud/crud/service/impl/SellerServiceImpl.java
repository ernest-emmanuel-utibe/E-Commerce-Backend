package com.crud.crud.service.impl;

import com.crud.crud.data.dto.SellerDto;
import com.crud.crud.data.dto.SessionDto;
import com.crud.crud.data.models.Seller;
import com.crud.crud.data.models.UserSession;
import com.crud.crud.data.repository.SellerDao;
import com.crud.crud.data.repository.SessionDao;
import com.crud.crud.exception.LoginException;
import com.crud.crud.exception.SellerException;
import com.crud.crud.service.LoginLogoutService;
import com.crud.crud.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
//    @Autowired
    private final SellerDao sellerDao;

//    @Autowired
    private final LoginLogoutService loginService;

//    @Autowired
    private final SessionDao sessionDao;


    @Override
    public Seller addSeller(Seller seller) {

        Seller add= sellerDao.save(seller);

        return add;
    }

    @Override
    public List<Seller> getAllSellers() throws SellerException {

        List<Seller> sellers= sellerDao.findAll();

        if(sellers.size()>0) {
            return sellers;
        }
        else throw new SellerException("No Seller Found !");

    }

    @Override
    public Seller getSellerById(Integer sellerId) {

        Optional<Seller> seller=sellerDao.findById(sellerId);

        if(seller.isPresent()) {
            return seller.get();
        }
        else throw new SellerException("Seller not found for this ID: "+sellerId);
    }

    @Override
    public Seller updateSeller(Seller seller, String token) {

        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        Seller existingSeller=sellerDao.findById(seller.getSellerId()).orElseThrow(()-> new SellerException("Seller not found for this Id: "+seller.getSellerId()));
        Seller newSeller= sellerDao.save(seller);
        return newSeller;
    }

    @Override
    public Seller deleteSellerById(Integer sellerId, String token) {

        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        Optional<Seller> opt=sellerDao.findById(sellerId);

        if(opt.isPresent()) {

            UserSession user = sessionDao.findByToken(token).get();

            Seller existingseller=opt.get();

            if(user.getUserId() == existingseller.getSellerId()) {
                sellerDao.delete(existingseller);

                // logic to log out a seller after he deletes his account
                SessionDto session = new SessionDto();
                session.setToken(token);
                loginService.logoutSeller(session);

                return existingseller;
            }
            else {
                throw new SellerException("Verification Error in deleting seller account");
            }
        }
        else throw new SellerException("Seller not found for this ID: "+sellerId);

    }

    @Override
    public Seller updateSellerMobile(SellerDto sellerdto, String token) throws SellerException {
        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Seller existingSeller=sellerDao.findById(user.getUserId()).orElseThrow(()->new SellerException("Seller not found for this ID: "+ user.getUserId()));

        if(existingSeller.getPassword().equals(sellerdto.getPassword())) {
            existingSeller.setMobile(sellerdto.getMobile());
            return sellerDao.save(existingSeller);
        }
        else {
            throw new SellerException("Error occured in updating mobile. Please enter correct password");
        }
    }

    @Override
    public Seller getSellerByMobile(String mobile, String token) throws SellerException {

        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        Seller existingSeller = sellerDao.findByMobile(mobile).orElseThrow( () -> new SellerException("Seller not found with given mobile"));

        return existingSeller;
    }

    @Override
    public Seller getCurrentlyLoggedInSeller(String token) throws SellerException{

        if(token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }

        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Seller existingSeller=sellerDao.findById(user.getUserId()).orElseThrow(()->new SellerException("Seller not found for this ID"));

        return existingSeller;

    }

    @Override
    public SessionDto updateSellerPassword(SellerDto sellerDTO, String token) throws SellerException {
        if (token.contains("seller") == false) {
            throw new LoginException("Invalid session token for seller");
        }


        loginService.checkTokenStatus(token);

        UserSession user = sessionDao.findByToken(token).get();

        Optional<Seller> opt = sellerDao.findById(user.getUserId());

        if (opt.isEmpty())
            throw new SellerException("Seller does not exist");

        Seller existingSeller = opt.get();


        if (sellerDTO.getMobile().equals(existingSeller.getMobile()) == false) {
            throw new SellerException("Verification error. Mobile number does not match");
        }

        existingSeller.setPassword(sellerDTO.getPassword());

        sellerDao.save(existingSeller);

        SessionDto session = new SessionDto();

        session.setToken(token);

        loginService.logoutSeller(session);

        session.setMessage("Updated password and logged out. Login again with new password");

        return session;
    }
}
