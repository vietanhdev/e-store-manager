package com.example.store.controller.report_management;

import java.util.List;

import javax.validation.Valid;

import com.example.store.model.product_management.Product;
import com.example.store.payload.report_management.request.Report1Request;
import com.example.store.payload.report_management.response.Report1Response;
import com.example.store.repository.buy_management.BuyItemRepository;
import com.example.store.repository.invoice_management.InvoiceRepository;
import com.example.store.repository.product_management.ProductRepository;
import com.example.store.repository.sell_management.BestSelling;
import com.example.store.repository.sell_management.SellItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ReportController {

    @Autowired
    private BuyItemRepository buyItemRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private SellItemRepository sellItemRepository;

    @Autowired
    private ProductRepository productRepository;

    // Admin, Manager
    @PostMapping("/reports/1")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> report1(@Valid @RequestBody Report1Request report1Request) {

        // get total cost
        Double buy_cost = buyItemRepository.totalBuy(report1Request.getStart(), report1Request.getEnd());

        Double invoice_cost =  invoiceRepository.totalInvoice(report1Request.getStart(), report1Request.getEnd());

        Double cost = 0D;

        if(buy_cost != null && invoice_cost != null){
            cost = buy_cost + invoice_cost;
        }

        // get total revenue
        Double sell_revenue = sellItemRepository.totalSell(report1Request.getStart(), report1Request.getEnd());

        Double revenue = 0D;

        if(sell_revenue != null){
            revenue = sell_revenue;
        }

        Report1Response report1Response = new Report1Response(cost.floatValue(), revenue.floatValue());

        // get the best selling
        List<BestSelling> bestSellings = sellItemRepository.bestSellings(report1Request.getStart(), report1Request.getEnd());


        Long count = 0L;
        for(BestSelling bestSelling: bestSellings) {    
            if(count == report1Request.getLength()) {
                break;
            }
            Long id = bestSelling.getProduct_id();
            Double sold_quantities = bestSelling.getSold_quantities();
            
            Product product = productRepository.findById(id).orElse(null);

            String name = null, unit = null, barcode = null;
            Float price = null, quantities = null;

            if(product != null){
                name = product.getName();
                price = product.getPrice();
                unit = product.getUnit();
                barcode = product.getBarcode();
                quantities = product.getQuantities();
            }

            bestSelling.getSold_quantities();

            report1Response.addProduct(id, name, price, unit, barcode, quantities, sold_quantities);
            count++;
        }

        return new ResponseEntity<>(report1Response, HttpStatus.OK);
    }
}