package com.example.store.repository.supplier_management;

import java.util.List;
import java.util.Optional;

import com.example.store.model.supplier_management.Supplier;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long>{
    Optional<Supplier> findById(Long id);

    List<Supplier> findAll();

    boolean existsById(Long id);

    @Query("SELECT e FROM Supplier e WHERE e.name LIKE %:name% AND e.email LIKE %:email% AND e.address LIKE %:address% AND e.mobileNo LIKE %:mobileNo% AND ( e.name LIKE %:value% OR e.email LIKE %:value% OR e.address LIKE %:value% OR e.mobileNo LIKE %:value% ) ORDER BY e.createdAt ASC")
    Page<Supplier> searchSuppliers(@Param("value") String value, @Param("name") String name, @Param("email") String email, @Param("address") String address, @Param("mobileNo") String mobileNo, Pageable pageable);

}