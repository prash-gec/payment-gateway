package com.checkout.dummy;

import com.checkout.domain.Transaction;
import com.checkout.utils.PaymentStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Dummy Bank client simulate any bank adapter in our application which have all the
 * basic money movement apis 'charge', 'refund', 'payment' as this is not part of assignment
 * so just basic implementation is added.
 */

@Configuration
public class DummyBankClient {

    @Value("#{'${valid.cc.series}'.split(',')}")
    List<String> validCardSeriesList;
    @Value("#{'${blocked.cc.series}'.split(',')}")
    List<String> blockedCardSeriesList;

    public PaymentStatus charge(Transaction transaction) {
        String ccNumber = transaction.getCardNumber();
        String ccSeries = ccNumber.substring(0, 4);
        if (blockedCardSeriesList.contains(ccSeries)) {
            return PaymentStatus.DENIED;
        }
        if (validCardSeriesList.contains(ccSeries))
            return PaymentStatus.SUCCESS;
        return PaymentStatus.FAILURE;
    }


}
