package za.ca.cput.commerce.service.impl;

/*
Author: Mogamad Jawaad Allie - 230472125
19/07/2026
 */

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ca.cput.commerce.domain.Invoice;
import za.ca.cput.commerce.repository.InvoiceRepository;
import za.ca.cput.commerce.service.InvoiceService;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public Invoice save(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice findById(String id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invoice"+ id));
    }

    @Override
    public Invoice updateStatus(String id, String status) {
        Invoice existing = findById(id);

        Invoice updated = new Invoice.Builder()
                .copy(existing)
                .setInvoiceStatus(status)
                .build();
        return invoiceRepository.save(updated);
    }

    @Override
    public void deleteById(String id) {
        invoiceRepository.delete(findById(id));
    }
}


