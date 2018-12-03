package com.example.store.model.customer_management;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.example.store.model.audit.DateAudit;

@Entity
@Table(name = "customers")
public class Customer extends DateAudit{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @Size(max = 40)
    @Email
    private String email;
    
    @Size(max = 100)
    private String address;

    @Size(max = 100)    
    private String mobileNo;

    public Customer(){

    }

    public Customer(String name, String email, String address, String mobileNo){
        this.name = name;
        this.email = email;
        this.address = address;
        this.mobileNo = mobileNo;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

}