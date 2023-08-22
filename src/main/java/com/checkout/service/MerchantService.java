package com.checkout.service;

import com.checkout.domain.MerchantDetail;
import com.checkout.repository.MerchantRepository;
import com.checkout.request.MerchantRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public Optional<MerchantDetail> getMerchant(UUID merchantId) {
        return merchantRepository.findById(merchantId);
    }


    public MerchantDetail createMerchant(MerchantRequest request) {
        MerchantDetail merchantDetail = new MerchantDetail();
        merchantDetail.setName(request.getName());
        return merchantRepository.save(merchantDetail);
    }

    public List<MerchantDetail> getMerchants(){
        return merchantRepository.findAll(); // pagination can be added later
    }


}
