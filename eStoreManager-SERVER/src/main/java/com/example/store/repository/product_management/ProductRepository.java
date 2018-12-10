package com.example.store.repository.product_management;

import java.util.List;
import java.util.Optional;

import com.example.store.model.product_management.Product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    Optional<Product> findById(Long id);

    List<Product> findAll();

    boolean existsById(Long id);

    @Query("SELECT e FROM Product e WHERE e.name LIKE %:name% AND e.unit LIKE %:unit% AND e.barcode LIKE %:barcode% AND ( e.name LIKE %:value% OR e.unit LIKE %:value% OR e.barcode LIKE %:value% ) ORDER BY e.createdAt ASC")
    Page<Product> searchProducts(@Param("value") String value, @Param("name") String name, @Param("unit") String unit, @Param("barcode") String barcode, Pageable pageable);

}