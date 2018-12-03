package com.example.store.model.product_management;

import javax.persistence.*;

import com.example.store.model.audit.DateAudit;

@Entity
@Table(name = "import_bills")
public class ImportBill extends DateAudit{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String user_id;

    public ImportBill() {

    }

    public ImportBill(String user_id) {
        this.user_id = user_id;
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }
}