package com.checkout.service;

import com.checkout.domain.CardDetail;
import com.checkout.domain.Transaction;
import com.checkout.repository.TransactionRepository;
import com.checkout.request.PaymentRequest;
import com.checkout.utils.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class MerchantService {

    private final TransactionRepository transactionRepository;
    private final PaymentGatewayService pgService;
    private final UUID merchantId = UUID.randomUUID();


    public Transaction payByCard(PaymentRequest paymentRequest){

        Transaction transaction = generateTransaction(paymentRequest);

        log.info("Initiated transaction {} to the bank!", transaction.getId());
        PaymentStatus status = null;
        try{
            status = performTransaction(transaction);
        }catch (Exception ex){
            log.error("Transaction {} failed with exception {}", transaction.getId(), ex.getMessage());
            status = PaymentStatus.FAILURE;
        }

        log.info("status of the transaction {} is {}", transaction.getId(), status);
        transaction.setStatus(status);
        transactionRepository.save(transaction);

        return transaction.maskCardNumber();
    }

    private PaymentStatus performTransaction(Transaction transaction){
        return pgService.processPayment(transaction, merchantId);
    }

    private Transaction generateTransaction(PaymentRequest paymentRequest){
        CardDetail cardDetail = paymentRequest.getCardDetail();
        Transaction initTransaction = Transaction.builder()
                .cardNumber(cardDetail.getCardNumber())
                .expiryMonth(cardDetail.getExpiryMonth())
                .expiryYear(cardDetail.getExpiryYear())
                .cvv(cardDetail.getCvv())
                .currency(paymentRequest.getAmount().getCurrency())
                .transactionValue(paymentRequest.getAmount().getValue())
                .status(PaymentStatus.INITIATED)
                .createdAt(LocalDateTime.now()).build();
        return transactionRepository.save(initTransaction);
    }


    public Optional<Transaction> fetchTransaction(UUID transactionId){
        return transactionRepository.findById(transactionId);
    }

    public List<Transaction> fetchAllTransaction(){
        return transactionRepository.findAll();
    }



}
