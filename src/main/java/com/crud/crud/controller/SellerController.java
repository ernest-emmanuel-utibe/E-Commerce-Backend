package com.crud.crud.controller;

import java.util.List;

import javax.validation.Valid;

import com.crud.crud.data.dto.SellerDto;
import com.crud.crud.data.dto.SessionDto;
import com.crud.crud.data.models.Seller;
import com.crud.crud.service.SellerService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SellerController {
//    @Autowired
    private final SellerService sellerService;

    @PostMapping("/add seller")
    public ResponseEntity<Seller> addSellerHandler(@Valid @RequestBody Seller seller){

        Seller addseller=sellerService.addSeller(seller);

        System.out.println("Seller"+ seller);

        return new ResponseEntity<>(addseller, HttpStatus.CREATED);
    }


    @GetMapping("/sellers")
    public ResponseEntity<List<Seller>> getAllSellerHandler(){

        List<Seller> sellers=sellerService.getAllSellers();

        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }


    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<Seller> getSellerByIdHandler(@PathVariable("sellerId") Integer Id){

        Seller getSeller=sellerService.getSellerById(Id);

        return new ResponseEntity<>(getSeller, HttpStatus.OK);
    }

    @GetMapping("/seller")
    public ResponseEntity<Seller> getSellerByMobileHandler(@RequestParam("mobile") String mobile, @RequestHeader("token") String token){

        Seller getSeller=sellerService.getSellerByMobile(mobile, token);

        return new ResponseEntity<>(getSeller, HttpStatus.OK);
    }

    @GetMapping("/seller/current")
    public ResponseEntity<Seller> getLoggedInSellerHandler(@RequestHeader("token") String token){

        Seller getSeller = sellerService.getCurrentlyLoggedInSeller(token);

        return new ResponseEntity<>(getSeller, HttpStatus.OK);
    }

    @PutMapping("/seller")
    public ResponseEntity<Seller> updateSellerHandler(@RequestBody Seller seller, @RequestHeader("token") String token){
        Seller updatedseller=sellerService.updateSeller(seller, token);

        return new ResponseEntity<>(updatedseller, HttpStatus.ACCEPTED);

    }


    @PutMapping("/seller/update/mobile")
    public ResponseEntity<Seller> updateSellerMobileHandler(@Valid @RequestBody SellerDto sellerdto, @RequestHeader("token") String token){
        Seller updatedseller=sellerService.updateSellerMobile(sellerdto, token);

        return new ResponseEntity<>(updatedseller,HttpStatus.ACCEPTED);

    }


    @PutMapping("/seller/update/password")
    public ResponseEntity<SessionDto> updateSellerPasswordHandler(@Valid @RequestBody SellerDto sellerDto, @RequestHeader("token") String token){
        return new ResponseEntity<>(sellerService.updateSellerPassword(sellerDto, token), HttpStatus.ACCEPTED);
    }


    @DeleteMapping("/seller/{sellerId}")
    public ResponseEntity<Seller> deleteSellerByIdHandler(@PathVariable("sellerId") Integer Id, @RequestHeader("token") String token){

        Seller deletedSeller=sellerService.deleteSellerById(Id, token);

        return new ResponseEntity<>(deletedSeller, HttpStatus.OK);

    }
}
