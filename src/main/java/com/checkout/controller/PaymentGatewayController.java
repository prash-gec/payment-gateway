package com.checkout.controller;

import com.checkout.domain.MerchantDetail;
import com.checkout.domain.Transaction;
import com.checkout.request.PaymentRequest;
import com.checkout.service.MerchantService;
import com.checkout.service.PaymentGatewayService;
import com.checkout.utils.CommonResponse;
import com.checkout.utils.PaymentStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/merchants")
@AllArgsConstructor
@Tag(name = "Merchant's Owned endpoint")
public class PaymentGatewayController {

    private final PaymentGatewayService pgService;
    private final MerchantService merchantService;

    @Operation(summary = "Users pay using their card details on merchant's endpoint")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment Successful!",
                    content = {@Content(mediaType = "", schema = @Schema(implementation = Transaction.class))}),
            @ApiResponse(responseCode = "400", description = "invalid Request data")
    }
    )
    @PostMapping("/{merchantId}/payments")
    public ResponseEntity<?> makePayment(@Valid @RequestBody PaymentRequest request, @PathVariable UUID merchantId) {
        Optional<MerchantDetail> optionalMerchant = merchantService.getMerchant(merchantId);
        if(optionalMerchant.isEmpty()){
            return CommonResponse.getNotFoundResponse("Invalid Merchant id!");
        }
        Transaction transaction = pgService.processPayment(request, merchantId);
        if (transaction.getStatus() != PaymentStatus.SUCCESS) {
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(transaction);
        }
        return ResponseEntity.ok(transaction.maskCardNumber());
    }

    @Operation(summary = "Users can fetch the payment details using the paymentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns successful response!"),
            @ApiResponse(responseCode = "404", description = "Payment data not found")
    }
    )
    @GetMapping("/{merchantId}/payments/{transactionId}")
    public ResponseEntity<?> fetchPayment(@PathVariable UUID transactionId) {
        Optional<Transaction> transaction = pgService.fetchTransaction(transactionId);
        if (transaction.isEmpty())
            return CommonResponse.getNotFoundResponse( "Invalid Transaction Id!");
        return ResponseEntity.ok(transaction.get().maskCardNumber());
    }

    @Operation(summary = "Users can fetch the payment details using the paymentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns successful response!"),
            @ApiResponse(responseCode = "404", description = "Payment data not found")
    }
    )
    @GetMapping("/{merchantId}/payments")
    public ResponseEntity<?> fetchAllPaymentForMerchantId(@PathVariable UUID merchantId) {
        List<Transaction> transactions = pgService.fetchAllTransaction(merchantId);
        if (transactions.isEmpty())
            return CommonResponse.getNotFoundResponse( "Invalid Transaction Id!");
        for(Transaction t : transactions)
            t.maskCardNumber();
        return ResponseEntity.ok(transactions);
    }


}
