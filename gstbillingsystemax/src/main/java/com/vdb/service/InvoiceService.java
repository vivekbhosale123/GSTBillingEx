package com.vdb.service;

import com.vdb.entity.Invoice;
import com.vdb.exception.RecordNotFoundException;
import com.vdb.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<Invoice> findAll()
    {
        return invoiceRepository.findAll();
    }

    public Optional<Invoice> findByInvoiceId(long invoiceId)
    {
        return Optional.of(invoiceRepository.findById(invoiceId).orElseThrow(() ->
                new RecordNotFoundException("Id not found")));
    }

    public Invoice findByName(String custName)
    {
        return invoiceRepository.findByCustName(custName);
    }

    public Invoice findByDate(Date date)
    {
        return invoiceRepository.findByInvoiceDate(date);
    }

    public Invoice findByEmail(String email)
    {
        return invoiceRepository.findByEmail(email);
    }

    public Invoice findByContact(long contact)
    {
        return invoiceRepository.findByContact(contact);
    }

    public Invoice updateInvoice(Invoice invoice)
    {
        return invoiceRepository.save(invoice);
    }

    public Invoice save(Invoice invoice)
    {
        double totalAmount=(invoice.getQuantity()* invoice.getPricePerUnit())*1.18;
        invoice.setTotalAmount(totalAmount);

        return invoiceRepository.save(invoice);
    }

    public void deleteInvoiceById(long invoiceId)
    {
        invoiceRepository.deleteById(invoiceId);
    }

}
