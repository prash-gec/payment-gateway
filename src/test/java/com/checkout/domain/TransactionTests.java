package com.checkout.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TransactionTests {

    @Test
    public void testMaskedCreditCardNumber(){
        String ccNumber = "1234567891234565";
        Transaction transaction = Transaction.builder().cardNumber(ccNumber).build();
        assertThat(transaction.getCardNumber()).isEqualTo(ccNumber);
        transaction.maskCardNumber();
        assertThat(transaction.getCardNumber()).isEqualTo("XXXXXXXXXXXX4565");
    }
}
