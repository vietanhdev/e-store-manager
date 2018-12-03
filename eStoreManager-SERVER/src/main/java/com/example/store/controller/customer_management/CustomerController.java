package com.example.store.controller.customer_management;

import javax.validation.Valid;

import com.example.store.model.customer_management.Customer;
import com.example.store.payload.common.response.ApiResponse;
import com.example.store.payload.customer_management.request.CreateCustomerRequest;
import com.example.store.payload.customer_management.request.SearchCustomersRequest;
import com.example.store.payload.customer_management.request.UpdateCustomerRequest;
import com.example.store.payload.customer_management.response.AllCustomerInforResponse;
import com.example.store.payload.customer_management.response.CreateCustomerResponse;
import com.example.store.payload.customer_management.response.CustomerInfor;
import com.example.store.payload.customer_management.response.CustomerInforResponse;
import com.example.store.payload.customer_management.response.Data;
import com.example.store.payload.customer_management.response.SearchCustomersResponse;
import com.example.store.repository.customer_management.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.store.util.AppConstants;
import com.example.store.util.OffsetBasedPageRequest;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    // Admin, Cashier create a new customer
    @PostMapping("/customers")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer(createCustomerRequest.getName(), 
                                        createCustomerRequest.getEmail(), 
                                        createCustomerRequest.getAddress(),
                                        createCustomerRequest.getMobileNo());

        Customer result = customerRepository.save(customer);

        return new ResponseEntity<>(new CreateCustomerResponse(result.getId()),
                                HttpStatus.OK);
    }

    // Admin, Cashier get a customer information
    @GetMapping("/customers/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> getCustomerInfor(@PathVariable(value = "id") String id) {
        try {
            Customer customer = customerRepository.findById(Long.parseLong(id)).orElse(null);
            CustomerInforResponse customerInforResponse = new CustomerInforResponse(customer.getId(),
                                                                                    customer.getName(),
                                                                                    customer.getEmail(),
                                                                                    customer.getAddress(),
                                                                                    customer.getMobileNo());

            return new ResponseEntity<>(customerInforResponse,
                                        HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_customer_id", "customer id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Cashier update a customer information
    @PutMapping("/customers/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> updateCustomerInfor(@PathVariable(value = "id") String id,
                                                @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        try {
            Customer customer = customerRepository.findById(Long.parseLong(id)).orElse(null);
            
            if(updateCustomerRequest.getName() != null) customer.setName(updateCustomerRequest.getName());
            if(updateCustomerRequest.getEmail() != null) customer.setEmail(updateCustomerRequest.getEmail());
            if(updateCustomerRequest.getAddress() != null) customer.setAddress(updateCustomerRequest.getAddress());
            if(updateCustomerRequest.getMobileNo() != null) customer.setMobileNo(updateCustomerRequest.getMobileNo());

            customerRepository.save(customer);
            return new ResponseEntity<>(new ApiResponse(true, "update_customer_information_successful", "update customer information successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_customer_id", "customer id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Cashier delete a customer
    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> deleteCustomer(@PathVariable(value = "id") String id) {
        try {
            Customer customer = customerRepository.findById(Long.parseLong(id)).orElse(null);
            customerRepository.delete(customer);
            return new ResponseEntity<>(new ApiResponse(true, "delete_customer_successful", "delete customer successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_customer_id", "customer id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Cashier find all customers pagable
    @GetMapping("/customers")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> getCustomersPagable(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        
        if(page < 0) {
            return new ResponseEntity<>( new ApiResponse(false, "page_num_unacceptable", "Page number cannot be less than zero."),
                                    HttpStatus.OK);
        }

        if(size > AppConstants.MAX_PAGE_SIZE) {
            return new ResponseEntity<>( new ApiResponse(false, "page_size_unacceptable", "Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE),
                                    HttpStatus.OK);
        }

        AllCustomerInforResponse allCustomerInforResponse = new AllCustomerInforResponse();

        // find all customers with page and size parameter
        Page<Customer> customers = customerRepository.getAllCustomersPagable(PageRequest.of(page, size));
        
        for(Customer customer: customers.getContent()) {
            CustomerInfor customerInfor = new CustomerInfor(customer.getId(), 
                                                            customer.getName(), 
                                                            customer.getEmail(), 
                                                            customer.getAddress(), 
                                                            customer.getMobileNo());
            allCustomerInforResponse.addCustomerInfor(customerInfor);
        }

        return new ResponseEntity<>(allCustomerInforResponse,
                                    HttpStatus.OK);                                          
    }

    // Admin, Cashier search customer
    @PostMapping("/search/customers")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> searchCustomers(@Valid @RequestBody SearchCustomersRequest searchCustomersRequest) {

        Pageable pageable = new OffsetBasedPageRequest(searchCustomersRequest.getStart(), searchCustomersRequest.getLength());

        if(searchCustomersRequest.getSearch().getValue() == null) searchCustomersRequest.getSearch().setValue("");
        if(searchCustomersRequest.getSearch().getName() == null) searchCustomersRequest.getSearch().setName("");
        if(searchCustomersRequest.getSearch().getEmail() == null) searchCustomersRequest.getSearch().setEmail("");
        if(searchCustomersRequest.getSearch().getAddress() == null) searchCustomersRequest.getSearch().setAddress("");
        if(searchCustomersRequest.getSearch().getMobileNo() == null) searchCustomersRequest.getSearch().setMobileNo("");

        Page<Customer> customers = customerRepository.searchCustomers(searchCustomersRequest.getSearch().getValue(),
                                                                    searchCustomersRequest.getSearch().getName(),
                                                                    searchCustomersRequest.getSearch().getEmail(), 
                                                                    searchCustomersRequest.getSearch().getAddress(), 
                                                                    searchCustomersRequest.getSearch().getMobileNo(),
                                                                    pageable);

        Long draw = searchCustomersRequest.getDraw() * 10;
        Long recordsTotal = (long) customerRepository.findAll().size();
        Long recordsFiltered = (long) customers.getTotalElements();

        SearchCustomersResponse searchCustomersResponse = new SearchCustomersResponse(draw, recordsTotal, recordsFiltered);

        for(Customer customer: customers.getContent()){
            Data data = new Data(customer.getId(), 
                                customer.getName(), 
                                customer.getEmail(), 
                                customer.getAddress(), 
                                customer.getMobileNo());

            searchCustomersResponse.addData(data);
        }
 
        return new ResponseEntity<>(searchCustomersResponse,
                                    HttpStatus.OK); 
    }

}