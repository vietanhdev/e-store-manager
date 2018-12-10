package com.example.store.repository.invoice_management;

import java.util.List;
import java.util.Optional;

import com.example.store.model.invoice_management.Invoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long>{
    Optional<Invoice> findById(Long id);

    List<Invoice> findAll();

    boolean existsById(Long id);

    @Query("SELECT e FROM Invoice e WHERE e.purpose LIKE %:purpose% AND e.description LIKE %:description% AND ( e.purpose LIKE %:value% OR e.description LIKE %:value% ) AND ( e.createdAt >= STR_TO_DATE(:start, '%Y-%m-%d %H:%i:%s') AND e.createdAt <= STR_TO_DATE(:end, '%Y-%m-%d %H:%i:%s') ) ORDER BY e.createdAt ASC")
    Page<Invoice> searchInvoices(@Param("value") String value, @Param("purpose") String purpose, @Param("description") String description, String start, String end, Pageable pageable);

    @Query("SELECT e FROM Invoice e WHERE e.purpose LIKE %:purpose% AND e.description LIKE %:description% AND ( e.purpose LIKE %:value% OR e.description LIKE %:value% ) ORDER BY e.createdAt ASC")
    Page<Invoice> findByString(@Param("value") String value, @Param("purpose") String purpose, @Param("description") String description, Pageable pageable);

    @Query("SELECT SUM(e.amount) FROM Invoice e WHERE e.createdAt >= STR_TO_DATE(:start, '%Y-%m-%d %H:%i:%s') AND e.createdAt <= STR_TO_DATE(:end, '%Y-%m-%d %H:%i:%s')")
    Double totalInvoice(String start, String end);
}