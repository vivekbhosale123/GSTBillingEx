package com.vdb.repository;

import com.vdb.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Invoice findByCustName(String custName);

    Invoice findByContact(long contact);

    Invoice findByEmail(String email);

    Invoice findByInvoiceDate(Date date);

}
