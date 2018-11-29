package com.example.store.repository.customer_management;

import java.util.Optional;

import com.example.store.model.customer_management.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findById(Long id);

    @Query("SELECT e FROM Customer e")
    Page<Customer> getAllCustomersPagable(Pageable pageable);

}