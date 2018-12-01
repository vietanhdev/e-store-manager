package com.example.store.repository.customer_management;

import java.util.List;
import java.util.Optional;

import com.example.store.model.customer_management.Customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    Optional<Customer> findById(Long id);

    List<Customer> findAll();

    @Query("SELECT e FROM Customer e")
    Page<Customer> getAllCustomersPagable(Pageable pageable);

    @Query("SELECT e FROM Customer e WHERE e.name LIKE %:name% AND e.email LIKE %:email% AND e.address LIKE %:address% AND e.mobileNo LIKE %:mobileNo% AND ( e.name LIKE %:data% OR e.email LIKE %:data% OR e.address LIKE %:data% OR e.mobileNo LIKE %:data% )")
    List<Customer> searchCustomers(@Param("data") String data, @Param("name") String name, @Param("email") String email, @Param("address") String address, @Param("mobileNo") String mobileNo, Pageable pageable);
}