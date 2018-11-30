package com.example.store.model.bill_management;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.example.store.model.audit.DateAudit;
import com.example.store.model.product_management.Product;
import com.example.store.model.user_management.User;

@Entity
@Table(name = "import_bills")
public class ImportBill extends DateAudit{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "import_bill_products",
            joinColumns = @JoinColumn(name = "import_bill_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private Set<Product> products = new HashSet<>();

    public ImportBill(){

    }

    public ImportBill(User user){
        this.user = user;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

}