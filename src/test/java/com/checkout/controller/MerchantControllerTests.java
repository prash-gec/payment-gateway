package com.checkout.controller;


import com.checkout.domain.MerchantDetail;
import com.checkout.domain.Transaction;
import com.checkout.request.Amount;
import com.checkout.request.CardDetail;
import com.checkout.request.PaymentRequest;
import com.checkout.service.MerchantService;
import com.checkout.service.PaymentGatewayService;
import com.checkout.utils.PaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(PaymentGatewayController.class)
public class MerchantControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentGatewayService pgService;

    @MockBean
    private MerchantService merchantService;


    @Test
    public void invalidPaymentRequest() throws Exception {
        UUID merchantId = UUID.randomUUID();
        this.mockMvc.perform(post("/api/merchants/"+merchantId+"/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getInvalidReq()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void validPaymentRequest() throws Exception {
        UUID merchantId = UUID.randomUUID();
        this.mockMvc.perform(post("/api/merchants/"+merchantId+"/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getValidReq(merchantId)))
                .andExpect(status().isOk());
    }

    public String getInvalidReq() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(
                new PaymentRequest(new Amount(123.0, "USD"), new CardDetail()));
    }

    public String getValidReq(UUID merchantID) throws JsonProcessingException {
        Amount amount = new Amount(123.0, "USD");
        CardDetail cardDetail = new CardDetail(
                "45678945612345", "12", "2025", "290", "Prashant Sharma");
        PaymentRequest request = new PaymentRequest(amount, cardDetail);
        when(pgService.processPayment(request, merchantID)).thenReturn(Transaction.builder().status(PaymentStatus.SUCCESS).build());
        MerchantDetail md = new MerchantDetail();
        md.setId(merchantID);
        when(merchantService.getMerchant(merchantID)).thenReturn(Optional.of(md));
        return new ObjectMapper().writeValueAsString(request);
    }
}