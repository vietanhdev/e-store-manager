package com.example.store.controller.sell_management;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.example.store.model.customer_management.Customer;
import com.example.store.model.product_management.Product;
import com.example.store.model.sell_management.Sell;
import com.example.store.model.sell_management.SellItem;
import com.example.store.model.user_management.User;
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
import com.example.store.repository.user_management.UserRepository;
import com.example.store.repository.customer_management.CustomerRepository;
import com.example.store.repository.product_management.ProductRepository;
import com.example.store.security.CurrentUser;
import com.example.store.security.UserPrincipal;
import com.example.store.util.AppConstants;
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
    private UserRepository userRepository;

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
                if(sellItemInfor.getQuantities() <= 0){
                    return new ResponseEntity<>(new ApiResponse(false, "product_quantities_unacceptable", "quantities of product must be greater than 0"),
                                        HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(new ApiResponse(false, "wrong_product_id", "product id must not be null"),
                                        HttpStatus.OK);
            }
            
        }

        // check Total wrong
        Float total = 0F;
        for(SellItemInfor sellItemInfor: createSellRequest.getSell_items()) {
            total += sellItemInfor.getPrice() *sellItemInfor.getQuantities();
        }
        total *= (1 + createSellRequest.getTax());
        Float result = Math.abs(total - createSellRequest.getTotal());

        if( result > AppConstants.EPS ) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_total_bill", "wrong total bill"),
                                        HttpStatus.OK);
        }
        
        // create new sell and save
        Sell sell = new Sell(currentUser.getId(), createSellRequest.getCustomer_id(), createSellRequest.getTax(), createSellRequest.getTotal());
        sell = sellRepository.save(sell);

        // create new sellItems and save
        for(SellItemInfor sellItemInfor: createSellRequest.getSell_items()) {
            // increase product quantities in product database
            Product product = productRepository.findById(sellItemInfor.getProduct_id()).orElse(null);
            if(product.getQuantities() < sellItemInfor.getQuantities()) {
                return new ResponseEntity<>(new ApiResponse(false, "not_enough_product", "there are only +" + product.getQuantities() + " items of product id " + sellItemInfor.getProduct_id() + " in warehouse"),
                                        HttpStatus.OK);
            } else {
                product.setQuantities(product.getQuantities() - sellItemInfor.getQuantities());
            }

            // write information to sellItem
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

            // find user name by id
            User user = userRepository.findById(sell.getUserId()).orElse(null);
            String user_name;
            if(user == null){
                user_name = null;
            } else {
                user_name = user.getName();
            }

            // find customer name by id
            String customer_name;
            if(sell.getCustomerId() == null) {
                customer_name = null;
            } else {
                Customer customer = customerRepository.findById(sell.getCustomerId()).orElse(null);
                if(customer == null){
                    customer_name = null;
                } else {
                    customer_name = customer.getName();
                }
            }

            // find date created at
            Date myDate = Date.from(sell.getCreatedAt());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = formatter.format(myDate);

            SellInforResponse sellInforResponse = new SellInforResponse(sell.getId(), sell.getUserId(), user_name, sell.getCustomerId(), customer_name, sell.getTax(), sell.getTotal(), sell.getActive(), formattedDate);

            List<SellItem> sellItems = sellItemRepository.findBySellId(sell_id);
            for(SellItem sellItem: sellItems) {

                // find product name, unit by id
                Product product = productRepository.findById(sellItem.getProductId()).orElse(null);
                String product_name, unit;
                if(product == null){
                    product_name = null;
                    unit = null;
                } else {
                    product_name = product.getName();
                    unit = product.getUnit();
                }

                sellInforResponse.addSell_items(sellItem.getProductId(),
                                            product_name,
                                            sellItem.getPrice(),
                                            sellItem.getQuantities(),
                                            unit);
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
            sell.setTax(updateSellRequest.getTax());
            if(updateSellRequest.getActive() != null) sell.setActive(updateSellRequest.getActive());

            // check Total wrong and unacceptable quantities
            Float total = 0F;
            for(SellItemInfor sellItemInfor: updateSellRequest.getSell_items()) {
                if(sellItemInfor.getQuantities() <= 0){
                    return new ResponseEntity<>(new ApiResponse(false, "product_quantities_unacceptable", "quantities of product must be greater than 0"),
                                        HttpStatus.OK);
                }

                total += sellItemInfor.getPrice() * sellItemInfor.getQuantities();
            }

            total *= (1 + updateSellRequest.getTax());
            Float result = Math.abs(total - updateSellRequest.getTotal());

            if( result > AppConstants.EPS ) {
                return new ResponseEntity<>(new ApiResponse(false, "wrong_total_bill", "wrong total bill"),
                                            HttpStatus.OK);
            }

            // save total
            sell.setTotal(updateSellRequest.getTotal());

            // delete old sell items
            List<SellItem> sellItems = sellItemRepository.findBySellId(sell_id);
            for(SellItem sellItem: sellItems) {
                sellItemRepository.deleteById(sellItem.getId());
            }
            
            // save new update from sell items
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

        } else if( searchSellsRequest.getSearch().getStart() == null 
            && searchSellsRequest.getSearch().getEnd() == null 
            && searchSellsRequest.getSearch().getCustomer_id() == null
            && searchSellsRequest.getSearch().getUser_id() != null ){

            sells = sellRepository.findByUserId(searchSellsRequest.getSearch().getUser_id(), pageable);
        
        } else if( searchSellsRequest.getSearch().getStart() == null 
            && searchSellsRequest.getSearch().getEnd() == null
            && searchSellsRequest.getSearch().getUser_id() == null
            && searchSellsRequest.getSearch().getCustomer_id() != null ){

            sells = sellRepository.findByCustomerId(searchSellsRequest.getSearch().getCustomer_id(), pageable);

        } else if( searchSellsRequest.getSearch().getStart() != null 
                && searchSellsRequest.getSearch().getEnd() != null
                && searchSellsRequest.getSearch().getUser_id() == null
                && searchSellsRequest.getSearch().getCustomer_id() == null ) {
            
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
            // find user name by id
            User user = userRepository.findById(sell.getUserId()).orElse(null);
            String user_name;
            if(user == null){
                user_name = null;
            } else {
                user_name = user.getName();
            }

            // find customer name by id
            String customer_name;
            if(sell.getCustomerId() == null) {
                customer_name = null;
            } else {
                Customer customer = customerRepository.findById(sell.getCustomerId()).orElse(null);
                if(customer == null){
                    customer_name = null;
                } else {
                    customer_name = customer.getName();
                }
            }

            // find date created at
            Date myDate = Date.from(sell.getCreatedAt());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = formatter.format(myDate);

            Data data = new Data(sell.getId(),
                                sell.getUserId(),
                                user_name,
                                sell.getCustomerId(),
                                customer_name,
                                sell.getTax(),
                                sell.getTotal(),
                                sell.getActive(),
                                formattedDate);

            List<SellItem> sellItems = sellItemRepository.findBySellId(sell.getId());
            for(SellItem sellItem: sellItems) {
                // find product name, unit by id
                Product product = productRepository.findById(sellItem.getProductId()).orElse(null);
                String product_name, unit;
                if(product == null){
                    product_name = null;
                    unit = null;
                } else {
                    product_name = product.getName();
                    unit = product.getUnit();
                }

                data.addSell_items(sellItem.getProductId(),
                                product_name,
                                sellItem.getPrice(),
                                sellItem.getQuantities(),
                                unit);
            }
            
            searchSellsResponse.addData(data);
        }

        return new ResponseEntity<>(searchSellsResponse,
                                    HttpStatus.OK); 
    }

}
    