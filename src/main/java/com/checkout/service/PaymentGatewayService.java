package com.checkout.service;

import com.checkout.domain.Transaction;
import com.checkout.dummy.DummyBankClient;
import com.checkout.repository.TransactionRepository;
import com.checkout.request.CardDetail;
import com.checkout.request.PaymentRequest;
import com.checkout.utils.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
     * @param request
     * @param merchantId
     * @return
     */
    public Transaction processPayment(PaymentRequest request, UUID merchantId) {
        Transaction transaction = generateTransaction(request, merchantId);

        log.info("Initiated transaction {} to the bank!", transaction.getId());
        PaymentStatus status = null;
        try {
            status = performTransaction(transaction);
        } catch (Exception ex) {
            log.error("Transaction {} failed with exception {}", transaction.getId(), ex.getMessage());
            status = PaymentStatus.FAILURE;
        }

        log.info("status of the transaction {} is {}", transaction.getId(), status);
        transaction.setStatus(status);
        transactionRepository.save(transaction);

        return transaction.maskCardNumber();
    }

    /**
     *
     * @param paymentRequest
     * @param merchantId
     * @return
     */
    private Transaction generateTransaction(PaymentRequest paymentRequest, UUID merchantId) {
        CardDetail cardDetail = paymentRequest.getCardDetail();
        Transaction initTransaction = Transaction.builder()
                .cardNumber(cardDetail.getCardNumber())
                .expiryMonth(cardDetail.getExpiryMonth())
                .expiryYear(cardDetail.getExpiryYear())
                .cvv(cardDetail.getCvv())
                .currency(paymentRequest.getAmount().getCurrency())
                .transactionValue(paymentRequest.getAmount().getValue())
                .status(PaymentStatus.INITIATED)
                .merchantId(merchantId)
                .createdAt(LocalDateTime.now()).build();
        return transactionRepository.save(initTransaction);
    }

    /**
     *
     * @param transaction
     * @return
     */
    private PaymentStatus performTransaction(Transaction transaction) {
        return bankClient.charge(transaction);
    }


    /**
     *
     * @param transactionId
     * @return
     */
    public Optional<Transaction> fetchTransaction(UUID transactionId) {
        return transactionRepository.findById(transactionId);
    }

    /**
     *
     * @return
     */
    public List<Transaction> fetchAllTransaction(UUID merchantId) {
        return transactionRepository.findByMerchantId(merchantId);
    }


}
