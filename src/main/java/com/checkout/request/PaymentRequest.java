package com.checkout.request;

import com.checkout.domain.Amount;
import com.checkout.domain.CardDetail;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class PaymentRequest {
    @Valid
    @NonNull
    private Amount amount;

    @Valid
    @JsonProperty("card_detail")
    private CardDetail cardDetail;
}
