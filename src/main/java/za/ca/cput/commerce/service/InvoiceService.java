package za.ca.cput.commerce.service;

/*
Author: Mogamad Jawaad Allie - 230472125
19/07/2026
 */

import za.ca.cput.commerce.domain.Invoice;

import java.util.List;

public interface InvoiceService {
    Invoice save(Invoice invoice);
    List<Invoice> findAll();
    Invoice findById(String id);
    Invoice updateStatus(String id, String status);
    void deleteById(String id);
}
