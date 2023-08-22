package com.checkout.service;

import com.checkout.dummy.DummyBankClient;
import com.checkout.domain.Transaction;
import com.checkout.repository.TransactionRepository;
import com.checkout.utils.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Ideally PG should store the transactions received as well,
 * but these are going to be the same transaction in our case so skipping for the assignment.
 */
@Service
@AllArgsConstructor
@Slf4j
public class PaymentGatewayService {

    private final TransactionRepository transactionRepository;

    private final DummyBankClient bankClient;

    /**
     *
     * @param transaction
     * @return Paymen
     */
    public PaymentStatus processPayment(Transaction transaction, UUID merchantId) {
        // Implement payment processing logic here
        // Simulate successful or failed payment
        log.info("Transaction id {} is received at payment gateway", transaction.getId());
        return bankClient.charge(transaction);
    }

    // Method to retrieve payment details by payment ID
    public Transaction getPaymentDetails(UUID paymentId) {
        return transactionRepository.findById(paymentId).orElse(null);
    }
}
