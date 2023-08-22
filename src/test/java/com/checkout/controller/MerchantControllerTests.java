package com.checkout.controller;


import com.checkout.request.Amount;
import com.checkout.request.CardDetail;
import com.checkout.domain.Transaction;
import com.checkout.request.PaymentRequest;
import com.checkout.service.MerchantService;
import com.checkout.utils.PaymentStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MerchantController.class)
public class MerchantControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MerchantService merchantService;


    @Test
    public void invalidPaymentRequest() throws Exception {
        this.mockMvc.perform(post("/merchant/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getInvalidReq()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void validPaymentRequest() throws Exception {
        this.mockMvc.perform(post("/merchant/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getValidReq()))
                .andExpect(status().isOk());
    }

    public String getInvalidReq() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(
                new PaymentRequest(new Amount(123.0, "USD"), new CardDetail()));
    }

    public String getValidReq() throws JsonProcessingException {
        Amount amount = new Amount(123.0, "USD");
        CardDetail cardDetail = new CardDetail(
                "45678945612345", "12", "2025", "290", "Prashant Sharma");
        PaymentRequest request = new PaymentRequest(amount, cardDetail);
        when(merchantService.payByCard(request)).thenReturn(Transaction.builder().status(PaymentStatus.SUCCESS).build());
        return new ObjectMapper().writeValueAsString(request);
    }
}