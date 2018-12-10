package com.example.store.repository.user_management;

import java.util.List;
import java.util.Optional;

import com.example.store.model.user_management.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    boolean existsById(Long id);

    List<User> findAll();

    @Query("SELECT e FROM User e ORDER BY e.createdAt ASC")
    Page<User> getAllUsersPagable(Pageable pageable);
} 