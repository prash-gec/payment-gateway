package com.checkout.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MerchantRequest {
    @NotNull
    String name;
}
