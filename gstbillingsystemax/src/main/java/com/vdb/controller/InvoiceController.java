package com.vdb.controller;

import com.vdb.entity.Email;
import com.vdb.entity.Invoice;
import com.vdb.exception.RecordNotFoundException;
import com.vdb.service.EmailService;
import com.vdb.service.InvoiceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/invoice")
@SecurityRequirement(name = "Bearer Auth")
@Slf4j
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/save")
    public ResponseEntity<Invoice> save(@Valid @RequestBody Invoice invoice) {
        return ResponseEntity.ok(invoiceService.save(invoice));
    }

    @GetMapping("/findbyid/{invoiceId}")
    public ResponseEntity<Optional<Invoice>> findById(@PathVariable long invoiceId) {
        return ResponseEntity.ok(invoiceService.findByInvoiceId(invoiceId));
    }

    @GetMapping("/findall")
    public ResponseEntity<List<Invoice>> findAll() {
        return ResponseEntity.ok(invoiceService.findAll());
    }

    @PostMapping("/sendemail")
    public ResponseEntity<String> sendEmail(String email) {
        emailService.sendMail(email);

        return ResponseEntity.ok("email sent successfully");
    }

    @PutMapping("/update/{invoiceId}")
    public ResponseEntity<String> updateInvoice(@PathVariable long invoiceId, @Valid @RequestBody Invoice invoice) {
        Invoice invoice1 = invoiceService.findByInvoiceId(invoiceId).orElseThrow(() ->
                new RecordNotFoundException("invoice id is not found"));

        invoice1.setCustName(invoice.getCustName());
        invoice1.setContact(invoice.getContact());
        invoice1.setEmail(invoice.getEmail());
        invoice1.setInvoiceDate(invoice.getInvoiceDate());
        invoice1.setPricePerUnit(invoice.getPricePerUnit());
        invoice1.setProductDescription(invoice.getProductDescription());
        invoice1.setQuantity(invoice.getQuantity());
        invoice1.setTotalAmount(invoice.getTotalAmount());

        invoiceService.updateInvoice(invoice1);

        return ResponseEntity.ok("Invoice Updated Successfully");
    }

    @DeleteMapping("/deletebyid/{invoiceId}")
    public ResponseEntity<String> deleteById(@PathVariable long invoiceId) {
        invoiceService.deleteInvoiceById(invoiceId);
        return ResponseEntity.ok("invoice deleted Successfully");
    }

    @GetMapping("/findbyname/{custName}")
    public ResponseEntity<Invoice> findByCustName(@PathVariable String custName) {
        return ResponseEntity.ok(invoiceService.findByName(custName));
    }

    @GetMapping("/findbydate/{date}")
    public ResponseEntity<Invoice> findByDate(@PathVariable Date date) {
        return ResponseEntity.ok(invoiceService.findByDate(date));
    }

    @GetMapping("/findbyemail/{email}")
    public ResponseEntity<Invoice> findByEmail(@PathVariable String email) {
        return ResponseEntity.ok(invoiceService.findByEmail(email));
    }

    @GetMapping("/findbycontact/{contact}")
    public ResponseEntity<Invoice> findByContact(@PathVariable long contact) {
        return ResponseEntity.ok(invoiceService.findByContact(contact));
    }

    @GetMapping("/findbyanyinput/{input}")
    public ResponseEntity<List<Invoice>> findByAnyInput(@PathVariable String input) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        return ResponseEntity.ok(invoiceService.findAll().
                stream().filter(emp -> simpleDateFormat.format(emp.getInvoiceDate()).equals(input)
                        || emp.getCustName().equals(input)
                        || String.valueOf(emp.getContact()).equals(input)
                        || emp.getEmail().equals(input))
                .toList());
    }

}
