package com.example.store.controller.buy_management;

import java.util.List;

import javax.validation.Valid;

import com.example.store.model.buy_management.Buy;
import com.example.store.model.buy_management.BuyItem;
import com.example.store.payload.buy_management.request.BuyItemInfor;
import com.example.store.payload.buy_management.request.CreateBuyRequest;
import com.example.store.payload.buy_management.request.SearchBuysRequest;
import com.example.store.payload.buy_management.request.UpdateBuyRequest;
import com.example.store.payload.buy_management.response.BuyInforResponse;
import com.example.store.payload.buy_management.response.CreateBuyResponse;
import com.example.store.payload.buy_management.response.Data;
import com.example.store.payload.buy_management.response.SearchBuysResponse;
import com.example.store.payload.common.response.ApiResponse;
import com.example.store.repository.buy_management.BuyRepository;
import com.example.store.repository.product_management.ProductRepository;
import com.example.store.repository.supplier_management.SupplierRepository;
import com.example.store.repository.user_management.UserRepository;
import com.example.store.util.OffsetBasedPageRequest;
import com.example.store.repository.buy_management.BuyItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BuyController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private BuyItemRepository buyItemRepository;

    // Admin, Manager create buy
    @PostMapping("/buys")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> createBuy(@Valid @RequestBody CreateBuyRequest createBuyRequest) {
        // check if user id exist
        Long user_id = createBuyRequest.getUser_id();
        if( userRepository.existsById(user_id) == false ) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_user_id", "user id " + user_id + " does not exist"),
                                    HttpStatus.OK);
        }

        // check if product id and supplier id exist
        for(BuyItemInfor buyItemInfor: createBuyRequest.getBuy_items()) {

            Long product_id = buyItemInfor.getProduct_id();
            if( productRepository.existsById(product_id) == false ) {
                return new ResponseEntity<>(new ApiResponse(false, "wrong_product_id", "product type id " + product_id + " does not exist"),
                                    HttpStatus.OK);
            }

            Long supplier_id = buyItemInfor.getSupplier_id();
            if( supplierRepository.existsById(supplier_id) == false ) {
                return new ResponseEntity<>(new ApiResponse(false, "wrong_supplier_id", "supplier id " + supplier_id + " does not exist"),
                                    HttpStatus.OK);
            }
        }

        // create new buy and save
        Buy buy = new Buy(createBuyRequest.getUser_id());
        buy = buyRepository.save(buy);

        // create new buyItems and save
        for(BuyItemInfor buyItemInfor: createBuyRequest.getBuy_items()) {
            BuyItem buyItem = new BuyItem(buyItemInfor.getProduct_id(), 
                        buyItemInfor.getSupplier_id(),
                        buyItemInfor.getPrice(),
                        buyItemInfor.getQuantities());
            buyItem.setBuy(buy);
            buyItemRepository.save(buyItem);
        }
        buyRepository.save(buy);

        return new ResponseEntity<>(new CreateBuyResponse(buy.getId()),
                                    HttpStatus.OK);
    }

    // Admin, Manager get buy
    @GetMapping("/buys/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> getBuyInfor(@PathVariable(value = "id") String id) {
        try {
            Long buy_id = Long.parseLong(id);
            Buy buy = buyRepository.findById(buy_id).orElse(null);
            BuyInforResponse buyInforResponse = new BuyInforResponse(buy.getId(), buy.getUserId());

            List<BuyItem> buyItems = buyItemRepository.findByBuyId(buy_id);
            for(BuyItem buyItem: buyItems) {
                buyInforResponse.addBuy_items(buyItem.getProductId(),
                                            buyItem.getSupplierId(),
                                            buyItem.getPrice(),
                                            buyItem.getQuantities());
            }
            System.out.println(buy.createdAt);

            return new ResponseEntity<>(buyInforResponse,
                                        HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_buy_id", "buy id " + id + " does not exist"),
                                        HttpStatus.OK);
        }
    }

    // Admin, Manager update buy
    @PutMapping("/buys/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> updateBuyInfor(@PathVariable(value = "id") String id,
                                                @Valid @RequestBody UpdateBuyRequest updateBuyRequest) {
        try {
            Long buy_id = Long.parseLong(id);
            Buy buy = buyRepository.findById(buy_id).orElse(null);

            buy.setUserId(updateBuyRequest.getUser_id());

            List<BuyItem> buyItems = buyItemRepository.findByBuyId(buy_id);
            for(BuyItem buyItem: buyItems) {
                buyItemRepository.deleteById(buyItem.getId());
            }
            
            for(BuyItemInfor buyItemInfor: updateBuyRequest.getBuy_items()) {
                BuyItem buyItem = new BuyItem(buyItemInfor.getProduct_id(), 
                                        buyItemInfor.getSupplier_id(),
                                        buyItemInfor.getPrice(),
                                        buyItemInfor.getQuantities());
                buyItem.setBuy(buy);
                buyItemRepository.save(buyItem);
            }

            buyRepository.save(buy);
            return new ResponseEntity<>(new ApiResponse(true, "update_buy_information_successful", "update buy information successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_buy_id", "buy id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager delete buy
    @DeleteMapping("/buys/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> deleteBuy(@PathVariable(value = "id") String id) {
        try {
            Buy buy = buyRepository.findById(Long.parseLong(id)).orElse(null);
            buyRepository.delete(buy);
            return new ResponseEntity<>(new ApiResponse(true, "delete_buy_successful", "delete buy successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_buy_id", "buy id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager search buy
    @PostMapping("/search/buys")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> searchBuys(@Valid @RequestBody SearchBuysRequest searchBuysRequest) {

        Pageable pageable = new OffsetBasedPageRequest(searchBuysRequest.getStart(), searchBuysRequest.getLength());

        Page<Buy> buys;

        if(searchBuysRequest.getSearch().getStart() != null 
            && searchBuysRequest.getSearch().getEnd() != null 
            && searchBuysRequest.getSearch().getUser_id() != null){

            buys = buyRepository.findByUserIdAndDate(searchBuysRequest.getSearch().getUser_id(),
                                                                searchBuysRequest.getSearch().getStart(),
                                                                searchBuysRequest.getSearch().getEnd(),
                                                                pageable);

        } else if( (searchBuysRequest.getSearch().getStart() == null 
            || searchBuysRequest.getSearch().getEnd() == null) 
            && searchBuysRequest.getSearch().getUser_id() != null ){

            buys = buyRepository.findByUserId(searchBuysRequest.getSearch().getUser_id(), pageable);

        } else if( searchBuysRequest.getSearch().getStart() != null 
                && searchBuysRequest.getSearch().getEnd() != null ) {
            
            buys = buyRepository.findByDate(searchBuysRequest.getSearch().getStart(),
                                                    searchBuysRequest.getSearch().getEnd(),
                                                    pageable);

        } else {

            buys = buyRepository.findAll(pageable);

        }
        
        Long draw = searchBuysRequest.getDraw() * 10;
        Long recordsTotal = (long) buyRepository.findAll().size();
        Long recordsFiltered = (long) buys.getTotalElements();                                        
        
        SearchBuysResponse searchBuysResponse = new SearchBuysResponse(draw, recordsTotal, recordsFiltered);

        for(Buy buy: buys.getContent()) {
            Data data = new Data(buy.getId(),
                                buy.getUserId());

            searchBuysResponse.addData(data);
        }

        return new ResponseEntity<>(searchBuysResponse,
                                    HttpStatus.OK); 
    }

}
    