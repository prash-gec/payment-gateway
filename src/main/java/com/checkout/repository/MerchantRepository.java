package com.checkout.repository;

import com.checkout.domain.MerchantDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MerchantRepository extends JpaRepository<MerchantDetail, UUID> {
}
