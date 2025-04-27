package io.leeseunghee.api.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.leeseunghee.api.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
