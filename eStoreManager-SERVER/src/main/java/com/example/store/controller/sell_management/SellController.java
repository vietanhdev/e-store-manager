package com.example.store.controller.sell_management;

import java.util.List;

import javax.validation.Valid;

import com.example.store.model.sell_management.Sell;
import com.example.store.model.sell_management.SellItem;
import com.example.store.payload.sell_management.request.SellItemInfor;
import com.example.store.payload.sell_management.request.CreateSellRequest;
import com.example.store.payload.sell_management.request.SearchSellsRequest;
import com.example.store.payload.sell_management.request.UpdateSellRequest;
import com.example.store.payload.sell_management.response.SellInforResponse;
import com.example.store.payload.sell_management.response.CreateSellResponse;
import com.example.store.payload.sell_management.response.Data;
import com.example.store.payload.sell_management.response.SearchSellsResponse;
import com.example.store.payload.common.response.ApiResponse;
import com.example.store.repository.sell_management.SellRepository;
import com.example.store.repository.customer_management.CustomerRepository;
import com.example.store.repository.product_management.ProductRepository;
import com.example.store.security.CurrentUser;
import com.example.store.security.UserPrincipal;
import com.example.store.util.OffsetBasedPageRequest;
import com.example.store.repository.sell_management.SellItemRepository;

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
public class SellController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellRepository sellRepository;

    @Autowired
    private SellItemRepository sellItemRepository;

    // Admin, Manager create sell
    @PostMapping("/sells")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> createSell(@Valid @RequestBody CreateSellRequest createSellRequest, @CurrentUser UserPrincipal currentUser) {

        // check if customer id exist
        if(createSellRequest.getCustomer_id() != null){
            Long customer_id = createSellRequest.getCustomer_id();
            if( customerRepository.existsById(customer_id) == false ) {
                return new ResponseEntity<>(new ApiResponse(false, "wrong_customer_id", "customer id " + customer_id + " does not exist"),
                                        HttpStatus.OK);
            }
        }

        // check if product id exist
        for(SellItemInfor sellItemInfor: createSellRequest.getSell_items()) {
            
            if(sellItemInfor.getProduct_id() != null) {
                Long product_id = sellItemInfor.getProduct_id();
                if( productRepository.existsById(product_id) == false ) {
                    return new ResponseEntity<>(new ApiResponse(false, "wrong_product_id", "product type id " + product_id + " does not exist"),
                                        HttpStatus.OK);
                }
            }
            
        }

        // create new sell and save
        Sell sell = new Sell(currentUser.getId(), createSellRequest.getCustomer_id(), createSellRequest.getTax());
        sell = sellRepository.save(sell);

        // create new sellItems and save
        for(SellItemInfor sellItemInfor: createSellRequest.getSell_items()) {
            SellItem sellItem = new SellItem(sellItemInfor.getProduct_id(),
                                        sellItemInfor.getPrice(),
                                        sellItemInfor.getQuantities());
            sellItem.setSell(sell);
            sellItemRepository.save(sellItem);
        }
        sellRepository.save(sell);

        return new ResponseEntity<>(new CreateSellResponse(sell.getId()),
                                    HttpStatus.OK);
    }

    // Admin, Manager get sell
    @GetMapping("/sells/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> getSellInfor(@PathVariable(value = "id") String id) {
        try {
            Long sell_id = Long.parseLong(id);
            Sell sell = sellRepository.findById(sell_id).orElse(null);
            SellInforResponse sellInforResponse = new SellInforResponse(sell.getId(), sell.getUserId(), sell.getCustomerId(), sell.getTax(), sell.getActive());

            List<SellItem> sellItems = sellItemRepository.findBySellId(sell_id);
            for(SellItem sellItem: sellItems) {
                sellInforResponse.addSell_items(sellItem.getProductId(),
                                            sellItem.getPrice(),
                                            sellItem.getQuantities());
            }
            System.out.println(sell.createdAt);

            return new ResponseEntity<>(sellInforResponse,
                                        HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_sell_id", "sell id " + id + " does not exist"),
                                        HttpStatus.OK);
        }
    }

    // Admin, Manager update sell
    @PutMapping("/sells/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateSellInfor(@PathVariable(value = "id") String id,
                                                @Valid @RequestBody UpdateSellRequest updateSellRequest) {
        try {
            Long sell_id = Long.parseLong(id);
            Sell sell = sellRepository.findById(sell_id).orElse(null);

            if(updateSellRequest.getUser_id() != null) sell.setUserId(updateSellRequest.getUser_id());
            if(updateSellRequest.getCustomer_id() != null) sell.setCustomerId(updateSellRequest.getCustomer_id());
            if(updateSellRequest.getTax() != null) sell.setTax(updateSellRequest.getTax());
            if(updateSellRequest.getActive() != null) sell.setActive(updateSellRequest.getActive());

            List<SellItem> sellItems = sellItemRepository.findBySellId(sell_id);
            for(SellItem sellItem: sellItems) {
                sellItemRepository.deleteById(sellItem.getId());
            }
            
            for(SellItemInfor sellItemInfor: updateSellRequest.getSell_items()) {
                SellItem sellItem = new SellItem(sellItemInfor.getProduct_id(),
                                                sellItemInfor.getPrice(),
                                                sellItemInfor.getQuantities());
                sellItem.setSell(sell);
                sellItemRepository.save(sellItem);
            }

            sellRepository.save(sell);
            return new ResponseEntity<>(new ApiResponse(true, "update_sell_information_successful", "update sell information successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_sell_id", "sell id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager delete sell
    @DeleteMapping("/sells/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> deleteSell(@PathVariable(value = "id") String id) {
        try {
            Sell sell = sellRepository.findById(Long.parseLong(id)).orElse(null);
            sellRepository.delete(sell);
            return new ResponseEntity<>(new ApiResponse(true, "delete_sell_successful", "delete sell successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_sell_id", "sell id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager search sell
    @PostMapping("/search/sells")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> searchSells(@Valid @RequestBody SearchSellsRequest searchSellsRequest) {

        Pageable pageable = new OffsetBasedPageRequest(searchSellsRequest.getStart(), searchSellsRequest.getLength());

        Page<Sell> sells;

        if(searchSellsRequest.getSearch().getStart() != null 
            && searchSellsRequest.getSearch().getEnd() != null 
            && searchSellsRequest.getSearch().getUser_id() != null
            && searchSellsRequest.getSearch().getCustomer_id() != null){

            sells = sellRepository.findByUserIdAndCustomerIdAndDate(searchSellsRequest.getSearch().getUser_id(),
                                                                searchSellsRequest.getSearch().getCustomer_id(),
                                                                searchSellsRequest.getSearch().getStart(),
                                                                searchSellsRequest.getSearch().getEnd(),
                                                                pageable);

        } else if( (searchSellsRequest.getSearch().getStart() == null 
            || searchSellsRequest.getSearch().getEnd() == null) 
            && searchSellsRequest.getSearch().getCustomer_id() == null
            && searchSellsRequest.getSearch().getUser_id() != null ){

            sells = sellRepository.findByUserId(searchSellsRequest.getSearch().getUser_id(), pageable);
        
        } else if( (searchSellsRequest.getSearch().getStart() == null 
            || searchSellsRequest.getSearch().getEnd() == null) 
            && searchSellsRequest.getSearch().getUser_id() == null
            && searchSellsRequest.getSearch().getCustomer_id() != null ){

            sells = sellRepository.findByCustomerId(searchSellsRequest.getSearch().getCustomer_id(), pageable);

        } else if( searchSellsRequest.getSearch().getStart() != null 
                && searchSellsRequest.getSearch().getEnd() != null ) {
            
            sells = sellRepository.findByDate(searchSellsRequest.getSearch().getStart(),
                                            searchSellsRequest.getSearch().getEnd(),
                                            pageable);

        } else {

            sells = sellRepository.findAll(pageable);

        }
        
        Long draw = searchSellsRequest.getDraw() * 10;
        Long recordsTotal = (long) sellRepository.findAll().size();
        Long recordsFiltered = (long) sells.getTotalElements();                                        
        
        SearchSellsResponse searchSellsResponse = new SearchSellsResponse(draw, recordsTotal, recordsFiltered);

        for(Sell sell: sells.getContent()) {
            Data data = new Data(sell.getId(),
                                sell.getUserId(),
                                sell.getCustomerId(),
                                sell.getTax(),
                                sell.getActive());

            List<SellItem> sellItems = sellItemRepository.findBySellId(sell.getId());
            for(SellItem sellItem: sellItems) {
                data.addSell_items(sellItem.getProductId(),
                                sellItem.getPrice(),
                                sellItem.getQuantities());
            }
            
            searchSellsResponse.addData(data);
        }

        return new ResponseEntity<>(searchSellsResponse,
                                    HttpStatus.OK); 
    }

}
    