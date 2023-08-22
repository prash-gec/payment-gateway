package com.checkout.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class CardDetail {

    @NotNull
    @Pattern(regexp = "\\b\\d{13,16}\\b", message = "invalid credit card")
    @JsonProperty("card_number")
    String cardNumber;

    @NotNull
    @Min(value = 1, message = "Invalid Month")
    @Max(value = 12, message = "Invalid Month")
    @JsonProperty("expiry_month")
    String expiryMonth;

    @NotNull
    @Pattern(regexp = "^(20)\\d\\d", message = "Invalid year format")
    @JsonProperty("expiry_year")
    String expiryYear;

    @NotNull
    @Pattern(regexp = "^\\d{3,4}$", message = "invalid CVV format")
    String cvv;

    @NotNull
    String name;

}
