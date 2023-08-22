package com.checkout.service;

import com.checkout.domain.MerchantDetail;
import com.checkout.request.MerchantRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class MerchantServiceTests {

    @Autowired
    MerchantService merchantService;

    @Test
    public void createMerchantTest(){
        MerchantRequest merchantRequest = new MerchantRequest();
        MerchantDetail md = merchantService.createMerchant(merchantRequest);
        assertThat(md.getId()).isNotNull();
    }

    @Test
    public void getMerchantTest(){
        MerchantRequest merchantRequest = new MerchantRequest();
        MerchantDetail md = merchantService.createMerchant(merchantRequest);
        assertThat(md.getId()).isNotNull();
        Optional<MerchantDetail> optionalMerchantDetail = merchantService.getMerchant(md.getId());
        assertThat(optionalMerchantDetail.isEmpty()).isFalse();
    }
}
