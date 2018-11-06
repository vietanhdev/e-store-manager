package com.example.store.payload;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.*;

import com.example.store.model.Role;

public class ChangeUserProfileRequest {

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @Size(max = 15)
    private String username;

    @NotNull
    @Min(0)
    private Long salary;
    
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @Size(max = 100)
    private String address;

    @Pattern(regexp="(^$|[0-9]{10})")
    private String mobileNo;

    private Set<String> roles = new HashSet<>();

    public ChangeUserProfileRequest(String name, String username, Long salary, String email, String address,
            String mobileNo) {
        this.name = name;
        this.username = username;
        this.salary = salary;
        this.email = email;
        this.address = address;
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}