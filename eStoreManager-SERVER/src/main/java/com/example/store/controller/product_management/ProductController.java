package com.example.store.controller.product_management;

import com.example.store.repository.product_management.ProductRepository;
import com.example.store.repository.product_management.ProductTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductController {
    
    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    ProductTypeRepository productTypeRepository;

}