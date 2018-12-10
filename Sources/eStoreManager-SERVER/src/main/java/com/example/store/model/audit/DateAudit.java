package com.example.store.model.audit;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
    value = {"createdAt", "updatedAt"},
    allowGetters = true
)
public abstract class DateAudit implements Serializable{

    private static final long serialVersionUID = 1L;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    public Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
    

    public Instant getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt){
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt(){
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt){
        this.updatedAt = updatedAt;
    }
}