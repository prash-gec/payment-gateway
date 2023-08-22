package com.checkout.controller;

import com.checkout.domain.Transaction;
import com.checkout.request.PaymentRequest;
import com.checkout.response.ErrorResponse;
import com.checkout.service.MerchantService;
import com.checkout.utils.PaymentStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.models.media.MediaType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("merchant/api")
@AllArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;


    @Operation(summary = "Users pay using their card details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment Successful!", content = {@Content(mediaType = "", schema = @Schema(implementation = Transaction.class))}),
            @ApiResponse(responseCode = "400", description = "invalid Request data")
    }
            )
    @PostMapping("/payments")
    public ResponseEntity<?> createPayment(@Valid @RequestBody PaymentRequest request){
        // do validation
    Transaction transaction = merchantService.payByCard(request);
    if(transaction.getStatus()!= PaymentStatus.SUCCESS){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(transaction);
    }
    return ResponseEntity.ok(transaction);
    }

    @Operation(summary = "Users can fetch the payment details using the paymentId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns successful response!"),
            @ApiResponse(responseCode = "404", description = "Payment data not found")
    }
    )
    @GetMapping("/payments/{transactionId}")
    public ResponseEntity<?> fetchPayment(@PathVariable UUID transactionId){
        Optional<Transaction> transaction = merchantService.fetchTransaction(transactionId);
        if(transaction.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Invalid Transaction Id!"));
        return ResponseEntity.ok(transaction.get().maskCardNumber());

    }

    @GetMapping("/payments")
    public ResponseEntity<?> fetchAllPayment(){
        List<Transaction> transaction = merchantService.fetchAllTransaction();
        return ResponseEntity.ok(transaction);
    }
}
