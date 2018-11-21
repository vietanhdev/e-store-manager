package com.example.store.repository.user_management;

import java.util.Optional;

import com.example.store.model.user_management.Role;
import com.example.store.model.user_management.RoleName;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>{
    Optional<Role> findByName(RoleName roleName);
}