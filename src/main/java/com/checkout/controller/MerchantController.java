package com.checkout.controller;

import com.checkout.domain.MerchantDetail;
import com.checkout.request.MerchantRequest;
import com.checkout.service.MerchantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/merchants")
@AllArgsConstructor
@Tag(name = "Merchant details related operations")
public class MerchantController {

    private final MerchantService merchantService;

    @Operation(summary = "Create Merchant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Merchant Created!",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = MerchantDetail.class))}),
            @ApiResponse(responseCode = "400", description = "invalid Request data")
    }
    )
    @PostMapping
    public ResponseEntity<?> createMerchant(@Valid @RequestBody MerchantRequest request) {
        MerchantDetail merchantDetail = merchantService.createMerchant(request);
        return ResponseEntity.ok(merchantDetail);
    }

    @Operation(summary = "Fetch all Merchants")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "list of merchant returned",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))})
    }
    )
    @GetMapping
    public ResponseEntity<?> getMerchant() {
        List<MerchantDetail> merchantDetails = merchantService.getMerchants();
        return ResponseEntity.ok(merchantDetails);
    }


}
