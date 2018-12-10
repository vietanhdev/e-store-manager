package com.example.store.controller.supplier_management;

import javax.validation.Valid;

import com.example.store.model.supplier_management.Supplier;
import com.example.store.payload.common.response.ApiResponse;
import com.example.store.payload.supplier_management.request.CreateSupplierRequest;
import com.example.store.payload.supplier_management.request.SearchSuppliersRequest;
import com.example.store.payload.supplier_management.request.UpdateSupplierRequest;
import com.example.store.payload.supplier_management.response.CreateSupplierResponse;
import com.example.store.payload.supplier_management.response.Data;
import com.example.store.payload.supplier_management.response.SearchSuppliersResponse;
import com.example.store.payload.supplier_management.response.SupplierInforResponse;
import com.example.store.repository.supplier_management.SupplierRepository;
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
public class SupplierController {
    
    @Autowired
    SupplierRepository supplierRepository;

    // Admin, Manager create a new supplier
    @PostMapping("/suppliers")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> createSupplier(@Valid @RequestBody CreateSupplierRequest createSupplierRequest) {
        Supplier supplier = new Supplier(createSupplierRequest.getName(), 
                                        createSupplierRequest.getEmail(), 
                                        createSupplierRequest.getAddress(),
                                        createSupplierRequest.getMobileNo());

        Supplier result = supplierRepository.save(supplier);

        return new ResponseEntity<>(new CreateSupplierResponse(result.getId()),
                                    HttpStatus.OK);
    }

    // Admin, Manager get a supplier information
    @GetMapping("/suppliers/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> getSupplierInfor(@PathVariable(value = "id") String id) {
        try {
            Supplier supplier = supplierRepository.findById(Long.parseLong(id)).orElse(null);
            SupplierInforResponse supplierInforResponse = new SupplierInforResponse(supplier.getId(),
                                                                                    supplier.getName(),
                                                                                    supplier.getEmail(),
                                                                                    supplier.getAddress(),
                                                                                    supplier.getMobileNo());

            return new ResponseEntity<>(supplierInforResponse,
                                        HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_supplier_id", "supplier id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager update a supplier information
    @PutMapping("/suppliers/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> updateSupplierInfor(@PathVariable(value = "id") String id,
                                                @Valid @RequestBody UpdateSupplierRequest updateSupplierRequest) {
        try {
            Supplier supplier = supplierRepository.findById(Long.parseLong(id)).orElse(null);
            
            if(updateSupplierRequest.getName() != null) supplier.setName(updateSupplierRequest.getName());
            if(updateSupplierRequest.getEmail() != null) supplier.setEmail(updateSupplierRequest.getEmail());
            if(updateSupplierRequest.getAddress() != null) supplier.setAddress(updateSupplierRequest.getAddress());
            if(updateSupplierRequest.getMobileNo() != null) supplier.setMobileNo(updateSupplierRequest.getMobileNo());

            supplierRepository.save(supplier);
            return new ResponseEntity<>(new ApiResponse(true, "update_supplier_information_successful", "update supplier information successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_supplier_id", "supplier id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager delete a supplier
    @DeleteMapping("/suppliers/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> deleteSupplier(@PathVariable(value = "id") String id) {
        try {
            Supplier supplier = supplierRepository.findById(Long.parseLong(id)).orElse(null);
            supplierRepository.delete(supplier);
            return new ResponseEntity<>(new ApiResponse(true, "delete_supplier_successful", "delete supplier successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_supplier_id", "supplier id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager search supplier
    @PostMapping("/search/suppliers")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> searchSuppliers(@Valid @RequestBody SearchSuppliersRequest searchSuppliersRequest) {

        Pageable pageable = new OffsetBasedPageRequest(searchSuppliersRequest.getStart(), searchSuppliersRequest.getLength());

        if(searchSuppliersRequest.getSearch().getValue() == null) searchSuppliersRequest.getSearch().setValue("");
        if(searchSuppliersRequest.getSearch().getName() == null) searchSuppliersRequest.getSearch().setName("");
        if(searchSuppliersRequest.getSearch().getEmail() == null) searchSuppliersRequest.getSearch().setEmail("");
        if(searchSuppliersRequest.getSearch().getAddress() == null) searchSuppliersRequest.getSearch().setAddress("");
        if(searchSuppliersRequest.getSearch().getMobileNo() == null) searchSuppliersRequest.getSearch().setMobileNo("");

        Page<Supplier> suppliers = supplierRepository.searchSuppliers(searchSuppliersRequest.getSearch().getValue(),
                                                                    searchSuppliersRequest.getSearch().getName(),
                                                                    searchSuppliersRequest.getSearch().getEmail(), 
                                                                    searchSuppliersRequest.getSearch().getAddress(), 
                                                                    searchSuppliersRequest.getSearch().getMobileNo(),
                                                                    pageable);

        Long draw = searchSuppliersRequest.getDraw() * 10;
        Long recordsTotal = (long) supplierRepository.findAll().size();
        Long recordsFiltered = (long) suppliers.getTotalElements();

        SearchSuppliersResponse searchSuppliersResponse = new SearchSuppliersResponse(draw, recordsTotal, recordsFiltered);

        for(Supplier supplier: suppliers.getContent()){
            Data data = new Data(supplier.getId(), 
                                supplier.getName(), 
                                supplier.getEmail(), 
                                supplier.getAddress(), 
                                supplier.getMobileNo());

            searchSuppliersResponse.addData(data);
        }
 
        return new ResponseEntity<>(searchSuppliersResponse,
                                    HttpStatus.OK); 
    }

}