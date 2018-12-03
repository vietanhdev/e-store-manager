package com.example.store.controller.product_type_management;

import java.util.List;

import javax.validation.Valid;

import com.example.store.model.product_type_management.ProductType;
import com.example.store.payload.common.response.ApiResponse;
import com.example.store.payload.product_type_management.request.CreateProductTypeRequest;
import com.example.store.payload.product_type_management.request.SearchProductTypesRequest;
import com.example.store.payload.product_type_management.request.UpdateProductTypeRequest;
import com.example.store.payload.product_type_management.response.CreateProductTypeResponse;
import com.example.store.payload.product_type_management.response.Data;
import com.example.store.payload.product_type_management.response.ProductTypeInforResponse;
import com.example.store.payload.product_type_management.response.SearchProductTypesResponse;
import com.example.store.repository.product_type_management.ProductTypeRepository;
import com.example.store.util.OffsetBasedPageRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductTypeController {
    
    @Autowired
    ProductTypeRepository productTypeRepository;

    // Admin, Manager create a new product type
    @PostMapping("/product_types")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> createProductType(@Valid @RequestBody CreateProductTypeRequest createProductTypeRequest) {
        ProductType productType = new ProductType(createProductTypeRequest.getName(), 
                                        createProductTypeRequest.getPrice(), 
                                        createProductTypeRequest.getUnit(),
                                        createProductTypeRequest.getBarcode());

        ProductType result = productTypeRepository.save(productType);

        return new ResponseEntity<>(new CreateProductTypeResponse(result.getId()),
                                    HttpStatus.OK);
    }

    // Admin, Manager get a product type information
    @GetMapping("/product_types/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> getProductTypeInfor(@PathVariable(value = "id") String id) {
        try {
            ProductType productType = productTypeRepository.findById(Long.parseLong(id)).orElse(null);
            ProductTypeInforResponse productTypeInforResponse = new ProductTypeInforResponse(productType.getId(),
                                                                                            productType.getName(),
                                                                                            productType.getPrice(),
                                                                                            productType.getUnit(),
                                                                                            productType.getBarcode());

            return new ResponseEntity<>(productTypeInforResponse,
                                        HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_product_type_id", "product type id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager update a product type information
    @PutMapping("/product_types/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> updateProductTypeInfor(@PathVariable(value = "id") String id,
                                                @Valid @RequestBody UpdateProductTypeRequest updateProductTypeRequest) {
        try {
            ProductType productType = productTypeRepository.findById(Long.parseLong(id)).orElse(null);
            
            if(updateProductTypeRequest.getName() != null) productType.setName(updateProductTypeRequest.getName());
            if(updateProductTypeRequest.getPrice() != null) productType.setPrice(updateProductTypeRequest.getPrice());
            if(updateProductTypeRequest.getUnit() != null) productType.setUnit(updateProductTypeRequest.getUnit());
            if(updateProductTypeRequest.getBarcode() != null) productType.setBarcode(updateProductTypeRequest.getBarcode());

            productTypeRepository.save(productType);
            return new ResponseEntity<>(new ApiResponse(true, "update_product_type_information_successful", "update product type information successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_product_type_id", "product type id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager delete a product type
    @DeleteMapping("/product_types/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> deleteProductType(@PathVariable(value = "id") String id) {
        try {
            ProductType productType = productTypeRepository.findById(Long.parseLong(id)).orElse(null);
            productTypeRepository.delete(productType);
            return new ResponseEntity<>(new ApiResponse(true, "delete_productType_successful", "delete productType successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_product_type_id", "product type id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager search product types
    @PostMapping("/search/product_types")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> searchProductTypes(@Valid @RequestBody SearchProductTypesRequest searchProductTypesRequest) {

        Pageable pageable = new OffsetBasedPageRequest(searchProductTypesRequest.getStart(), searchProductTypesRequest.getLength());

        if(searchProductTypesRequest.getSearch().getValue() == null) searchProductTypesRequest.getSearch().setValue("");
        if(searchProductTypesRequest.getSearch().getName() == null) searchProductTypesRequest.getSearch().setName("");
        if(searchProductTypesRequest.getSearch().getUnit() == null) searchProductTypesRequest.getSearch().setUnit("");
        if(searchProductTypesRequest.getSearch().getBarcode() == null) searchProductTypesRequest.getSearch().setBarcode("");

        List<ProductType> productTypes = productTypeRepository.searchProductTypes(searchProductTypesRequest.getSearch().getValue(),
                                                                                searchProductTypesRequest.getSearch().getName(), 
                                                                                searchProductTypesRequest.getSearch().getUnit(), 
                                                                                searchProductTypesRequest.getSearch().getBarcode(),
                                                                                pageable);

        Long draw = searchProductTypesRequest.getDraw() * 10;
        Long recordsTotal = (long) productTypeRepository.findAll().size();
        Long recordsFiltered = (long) productTypes.size();

        SearchProductTypesResponse searchProductTypesResponse = new SearchProductTypesResponse(draw, recordsTotal, recordsFiltered);

        for(ProductType productType: productTypes){
            Data data = new Data(productType.getId(), 
                                productType.getName(), 
                                productType.getPrice(), 
                                productType.getUnit(), 
                                productType.getBarcode());

            searchProductTypesResponse.addData(data);
        }
 
        return new ResponseEntity<>(searchProductTypesResponse,
                                    HttpStatus.OK); 
    }
}