package com.example.store.controller.product_management;

import javax.validation.Valid;

import com.example.store.model.product_management.Product;
import com.example.store.payload.common.response.ApiResponse;
import com.example.store.payload.product_management.request.CreateProductRequest;
import com.example.store.payload.product_management.request.SearchProductsRequest;
import com.example.store.payload.product_management.request.UpdateProductRequest;
import com.example.store.payload.product_management.response.CreateProductResponse;
import com.example.store.payload.product_management.response.Data;
import com.example.store.payload.product_management.response.ProductInforResponse;
import com.example.store.payload.product_management.response.SearchProductsResponse;
import com.example.store.repository.product_management.ProductRepository;
import com.example.store.util.OffsetBasedPageRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    
    @Autowired
    ProductRepository productRepository;

    // Admin, Manager create a new product type
    @PostMapping("/products")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> createProduct(@Valid @RequestBody CreateProductRequest createProductRequest) {
        Product product = new Product(createProductRequest.getName(), 
                                        createProductRequest.getPrice(), 
                                        createProductRequest.getUnit(),
                                        createProductRequest.getBarcode(),
                                        createProductRequest.getQuantities());

        Product result = productRepository.save(product);

        return new ResponseEntity<>(new CreateProductResponse(result.getId()),
                                    HttpStatus.OK);
    }

    // Admin, Manager get a product type information
    @GetMapping("/products/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> getProductInfor(@PathVariable(value = "id") String id) {
        try {
            Product product = productRepository.findById(Long.parseLong(id)).orElse(null);
            ProductInforResponse productInforResponse = new ProductInforResponse(product.getId(),
                                                                                            product.getName(),
                                                                                            product.getPrice(),
                                                                                            product.getUnit(),
                                                                                            product.getBarcode(),
                                                                                            product.getQuantities());

            return new ResponseEntity<>(productInforResponse,
                                        HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_product_id", "product type id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager update a product type information
    @PutMapping("/products/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> updateProductInfor(@PathVariable(value = "id") String id,
                                                @Valid @RequestBody UpdateProductRequest updateProductRequest) {
        try {
            Product product = productRepository.findById(Long.parseLong(id)).orElse(null);
            
            if(updateProductRequest.getName() != null) product.setName(updateProductRequest.getName());
            if(updateProductRequest.getPrice() != null) product.setPrice(updateProductRequest.getPrice());
            if(updateProductRequest.getUnit() != null) product.setUnit(updateProductRequest.getUnit());
            if(updateProductRequest.getBarcode() != null) product.setBarcode(updateProductRequest.getBarcode());
            if(updateProductRequest.getQuantities() != null) product.setQuantities(updateProductRequest.getQuantities());

            productRepository.save(product);
            return new ResponseEntity<>(new ApiResponse(true, "update_product_information_successful", "update product type information successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_product_id", "product type id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager delete a product type
    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") String id) {
        try {
            Product product = productRepository.findById(Long.parseLong(id)).orElse(null);
            productRepository.delete(product);
            return new ResponseEntity<>(new ApiResponse(true, "delete_product_successful", "delete product successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_product_id", "product type id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager search product types
    @PostMapping("/search/products")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> searchProducts(@Valid @RequestBody SearchProductsRequest searchProductsRequest) {

        Pageable pageable = new OffsetBasedPageRequest(searchProductsRequest.getStart(), searchProductsRequest.getLength());

        if(searchProductsRequest.getSearch().getValue() == null) searchProductsRequest.getSearch().setValue("");
        if(searchProductsRequest.getSearch().getName() == null) searchProductsRequest.getSearch().setName("");
        if(searchProductsRequest.getSearch().getUnit() == null) searchProductsRequest.getSearch().setUnit("");
        if(searchProductsRequest.getSearch().getBarcode() == null) searchProductsRequest.getSearch().setBarcode("");

        Page<Product> products = productRepository.searchProducts(searchProductsRequest.getSearch().getValue(),
                                                                                searchProductsRequest.getSearch().getName(), 
                                                                                searchProductsRequest.getSearch().getUnit(),
                                                                                searchProductsRequest.getSearch().getBarcode(),
                                                                                pageable);

        Long draw = searchProductsRequest.getDraw() * 10;
        Long recordsTotal = (long) productRepository.findAll().size();
        Long recordsFiltered = (long) products.getTotalElements();

        SearchProductsResponse searchProductsResponse = new SearchProductsResponse(draw, recordsTotal, recordsFiltered);

        for(Product product: products.getContent()){
            Data data = new Data(product.getId(), 
                                product.getName(), 
                                product.getPrice(), 
                                product.getUnit(), 
                                product.getBarcode(),
                                product.getQuantities());

            searchProductsResponse.addData(data);
        }
 
        return new ResponseEntity<>(searchProductsResponse,
                                    HttpStatus.OK); 
    }
}