package com.example.store.controller.invoice_management;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import com.example.store.model.invoice_management.Invoice;
import com.example.store.payload.common.response.ApiResponse;
import com.example.store.payload.invoice_management.request.CreateInvoiceRequest;
import com.example.store.payload.invoice_management.request.SearchInvoicesRequest;
import com.example.store.payload.invoice_management.request.UpdateInvoiceRequest;
import com.example.store.payload.invoice_management.response.CreateInvoiceResponse;
import com.example.store.payload.invoice_management.response.Data;
import com.example.store.payload.invoice_management.response.SearchInvoicesResponse;
import com.example.store.payload.invoice_management.response.InvoiceInforResponse;
import com.example.store.repository.invoice_management.InvoiceRepository;
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
public class InvoiceController {
    
    @Autowired
    InvoiceRepository invoiceRepository;

    // Admin, Manager create a new invoice
    @PostMapping("/invoices")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> createInvoice(@Valid @RequestBody CreateInvoiceRequest createInvoiceRequest) {
        Invoice invoice = new Invoice(createInvoiceRequest.getAmount(), 
                                        createInvoiceRequest.getPurpose(), 
                                        createInvoiceRequest.getDescription());

        Invoice result = invoiceRepository.save(invoice);

        return new ResponseEntity<>(new CreateInvoiceResponse(result.getId()),
                                    HttpStatus.OK);
    }

    // Admin, Manager get a invoice information
    @GetMapping("/invoices/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> getInvoiceInfor(@PathVariable(value = "id") String id) {
        try {
            Invoice invoice = invoiceRepository.findById(Long.parseLong(id)).orElse(null);

            // find date created at
            Date myDate = Date.from(invoice.getCreatedAt());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = formatter.format(myDate);

            InvoiceInforResponse invoiceInforResponse = new InvoiceInforResponse(invoice.getId(),
                                                                                invoice.getAmount(),
                                                                                invoice.getPurpose(),
                                                                                invoice.getDescription(),
                                                                                formattedDate);

            return new ResponseEntity<>(invoiceInforResponse,
                                        HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_invoice_id", "invoice id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager update a invoice information
    @PutMapping("/invoices/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> updateInvoiceInfor(@PathVariable(value = "id") String id,
                                                @Valid @RequestBody UpdateInvoiceRequest updateInvoiceRequest) {
        try {
            Invoice invoice = invoiceRepository.findById(Long.parseLong(id)).orElse(null);
            
            if(updateInvoiceRequest.getAmount() != null) invoice.setAmount(updateInvoiceRequest.getAmount());
            if(updateInvoiceRequest.getPurpose() != null) invoice.setPurpose(updateInvoiceRequest.getPurpose());
            if(updateInvoiceRequest.getDescription() != null) invoice.setDescription(updateInvoiceRequest.getDescription());

            invoiceRepository.save(invoice);
            return new ResponseEntity<>(new ApiResponse(true, "update_invoice_information_successful", "update invoice information successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_invoice_id", "invoice id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager delete a invoice
    @DeleteMapping("/invoices/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> deleteInvoice(@PathVariable(value = "id") String id) {
        try {
            Invoice invoice = invoiceRepository.findById(Long.parseLong(id)).orElse(null);
            invoiceRepository.delete(invoice);
            return new ResponseEntity<>(new ApiResponse(true, "delete_invoice_successful", "delete invoice successful"),
                                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, "wrong_invoice_id", "invoice id " + id + " does not exist"),
                                    HttpStatus.OK);
        }
    }

    // Admin, Manager search invoice
    @PostMapping("/search/invoices")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> searchInvoices(@Valid @RequestBody SearchInvoicesRequest searchInvoicesRequest) {

        Pageable pageable = new OffsetBasedPageRequest(searchInvoicesRequest.getStart(), searchInvoicesRequest.getLength());

        if(searchInvoicesRequest.getSearch().getValue() == null) searchInvoicesRequest.getSearch().setValue("");
        if(searchInvoicesRequest.getSearch().getPurpose() == null) searchInvoicesRequest.getSearch().setPurpose("");
        if(searchInvoicesRequest.getSearch().getDescription() == null) searchInvoicesRequest.getSearch().setDescription("");

        Page<Invoice> invoices;

        if(searchInvoicesRequest.getSearch().getStart() == null &&
        searchInvoicesRequest.getSearch().getEnd() == null) {
            invoices = invoiceRepository.findByString(searchInvoicesRequest.getSearch().getValue(),
            searchInvoicesRequest.getSearch().getPurpose(), 
            searchInvoicesRequest.getSearch().getDescription(),
            pageable); 
        } else {
            invoices = invoiceRepository.searchInvoices(searchInvoicesRequest.getSearch().getValue(),
            searchInvoicesRequest.getSearch().getPurpose(), 
            searchInvoicesRequest.getSearch().getDescription(),
            searchInvoicesRequest.getSearch().getStart(),
            searchInvoicesRequest.getSearch().getEnd(),
            pageable); 
        }

        Long draw = searchInvoicesRequest.getDraw() * 10;
        Long recordsTotal = (long) invoiceRepository.findAll().size();
        Long recordsFiltered = (long) invoices.getTotalElements();

        SearchInvoicesResponse searchInvoicesResponse = new SearchInvoicesResponse(draw, recordsTotal, recordsFiltered);

        for(Invoice invoice: invoices.getContent()){
            // find date created at
            Date myDate = Date.from(invoice.getCreatedAt());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedDate = formatter.format(myDate);
            
            Data data = new Data(invoice.getId(), 
                                invoice.getAmount(), 
                                invoice.getPurpose(), 
                                invoice.getDescription(),
                                formattedDate);

            searchInvoicesResponse.addData(data);
        }
 
        return new ResponseEntity<>(searchInvoicesResponse,
                                    HttpStatus.OK); 
    }

}