package com.checkout.domain;

import com.checkout.utils.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @JsonProperty("merchant_id")
    private UUID merchantId;

    private Currency currency;
    @JsonProperty("transaction_value")
    private BigDecimal transactionValue;
    private PaymentStatus status; // enum: SUCCESS, FAILURE etc.
    @JsonProperty("card_number")
    private String cardNumber;
    @JsonProperty("expiry_month")
    private String expiryMonth;
    @JsonProperty("expiry_year")
    private String expiryYear;
    private String cvv;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;


    public Transaction maskCardNumber() {

        if (cardNumber != null && cardNumber.length() > 4) {
            int length = cardNumber.length();

            this.setCardNumber("X".repeat(length - 4) + cardNumber.substring(length - 4));
        }
        return this;
    }
}
