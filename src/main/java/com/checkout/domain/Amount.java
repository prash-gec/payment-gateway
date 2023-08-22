package com.checkout.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Currency;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Amount {
    private BigDecimal value;
    private Currency currency;

    public Amount(Double val, String currency){
        new Amount (new BigDecimal(val), Currency.getInstance(currency));
    }
}
