package com.example.store.repository.product_type_management;

import java.util.List;
import java.util.Optional;

import com.example.store.model.product_type_management.ProductType;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Long>{
    Optional<ProductType> findById(Long id);

    List<ProductType> findAll();

    @Query("SELECT e FROM ProductType e WHERE e.name LIKE %:name% AND e.price LIKE %:price% AND e.unit LIKE %:unit% AND e.barcode LIKE %:barcode% AND ( e.name LIKE %:value% OR e.price LIKE %:value% OR e.unit LIKE %:value% OR e.barcode LIKE %:value% )")
    List<ProductType> searchProductTypes(@Param("value") String value, @Param("name") String name, @Param("price") String price, @Param("unit") String unit, @Param("barcode") String barcode, Pageable pageable);

}