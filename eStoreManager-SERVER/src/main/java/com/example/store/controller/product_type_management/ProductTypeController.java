package com.example.store.controller.product_type_management;

import com.example.store.repository.product_management.ProductRepository;
import com.example.store.repository.product_type_management.ProductTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;

public class ProductTypeController {
    
    @Autowired
    ProductRepository productRepository;
    
    @Autowired
    ProductTypeRepository productTypeRepository;

}