package com.example.store.controller.customer_management;

import javax.validation.Valid;

import com.example.store.model.customer_management.Customer;
import com.example.store.payload.common.response.ApiResponse;
import com.example.store.payload.customer_management.request.CreateCustomerRequest;
import com.example.store.payload.customer_management.request.UpdateCustomerRequest;
import com.example.store.payload.customer_management.response.AllCustomerSummaryResponse;
import com.example.store.payload.customer_management.response.CreateCustomerResponse;
import com.example.store.payload.customer_management.response.CustomerInforResponse;
import com.example.store.repository.customer_management.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
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

        return new ResponseEntity<>(new CreateCustomerResponse(true, result.getId()),
                                HttpStatus.ACCEPTED);
    }

    // Admin, Cashier get all customer summary informations
    @GetMapping("/customers")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> getAllCustomerProfile() {
        AllCustomerSummaryResponse allCustomerSummaryResponse = new AllCustomerSummaryResponse(true);
        for(Customer customer: customerRepository.findAll()){
            allCustomerSummaryResponse.addCustomer(customer.getId(),
                                                   customer.getName());
        }

        return new ResponseEntity<>(allCustomerSummaryResponse,
                                    HttpStatus.ACCEPTED);
    }

    // Admin, Cashier get a customer information
    @GetMapping("/customers/{customer_id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> getCustomerInfor(@PathVariable(value = "customer_id") String customer_id) {
        try {
            Customer customer;
            customer = customerRepository.findById(Long.parseLong(customer_id)).orElse(null);
            CustomerInforResponse customerInforResponse = new CustomerInforResponse(true, 
                                                                                    customer.getId(),
                                                                                    customer.getName(),
                                                                                    customer.getEmail(),
                                                                                    customer.getAddress(),
                                                                                    customer.getMobileNo());

            return new ResponseEntity<>(customerInforResponse,
                                        HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "something_wrong", "something wrong with customer id"),
                                    HttpStatus.ACCEPTED);
        }
    }

    // Admin, Cashier update a customer information
    @PutMapping("/customers/{customer_id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> updateCustomerInfor(@PathVariable(value = "customer_id") String customer_id,
                                                @Valid @RequestBody UpdateCustomerRequest updateCustomerRequest) {
        try {
            Customer customer;
            customer = customerRepository.findById(Long.parseLong(customer_id)).orElse(null);
            
            if(updateCustomerRequest.getName() != null) customer.setName(updateCustomerRequest.getName());
            if(updateCustomerRequest.getEmail() != null) customer.setEmail(updateCustomerRequest.getEmail());
            if(updateCustomerRequest.getAddress() != null) customer.setAddress(updateCustomerRequest.getAddress());
            if(updateCustomerRequest.getMobileNo() != null) customer.setMobileNo(updateCustomerRequest.getMobileNo());

            customerRepository.save(customer);
            return new ResponseEntity<>(new ApiResponse(true, "update_successful", "update successful"),
                                    HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "something_wrong", "something wrong with customer id"),
                                    HttpStatus.ACCEPTED);
        }
    }

    // Admin, Cashier delete a customer
    @DeleteMapping("/customers/{customer_id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CASHIER')")
    public ResponseEntity<?> deleteCustomer(@PathVariable(value = "customer_id") String customer_id) {
        try {
            Customer customer;
            customer = customerRepository.findById(Long.parseLong(customer_id)).orElse(null);
            customerRepository.delete(customer);
            return new ResponseEntity<>(new ApiResponse(true, "success", "delete customer successful"),
                                    HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "something_wrong", "something wrong with customer id"),
                                    HttpStatus.ACCEPTED);
        }
    }

}