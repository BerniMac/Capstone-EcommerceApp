package za.ca.cput.commerce.service;

/*
Author: Mogamad Jawaad Allie - 230472125
19/07/2026
 */
import za.ca.cput.commerce.domain.Payment;
import java.util.List;

public interface PaymentService {
    Payment save(Payment payment);
    List<Payment> findAll();
    Payment findById(String id);
    void deleteById(String id);
}
