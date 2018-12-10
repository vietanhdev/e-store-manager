package com.example.store.repository.buy_management;

import java.util.Optional;

import com.example.store.model.buy_management.Buy;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyRepository extends JpaRepository<Buy, Long> {
    Optional<Buy> findById(Long id);

    @Query("SELECT e from Buy e WHERE e.user_id = :id AND e.createdAt >= STR_TO_DATE(:start, '%Y-%m-%d %H:%i:%s') AND e.createdAt <= STR_TO_DATE(:end, '%Y-%m-%d %H:%i:%s')  ORDER BY e.createdAt ASC")
    Page<Buy> findByUserIdAndDate(Long id, String start, String end, Pageable pageable);

    @Query("SELECT e from Buy e WHERE e.user_id = :id  ORDER BY e.createdAt ASC")
    Page<Buy> findByUserId(Long id, Pageable pageable);

    @Query("SELECT e from Buy e WHERE e.createdAt >= STR_TO_DATE(:start, '%Y-%m-%d %H:%i:%s') AND e.createdAt <= STR_TO_DATE(:end, '%Y-%m-%d %H:%i:%s') ORDER BY e.createdAt ASC")
    Page<Buy> findByDate(String start, String end, Pageable pageable);

    Page<Buy> findAll(Pageable pageable);
}