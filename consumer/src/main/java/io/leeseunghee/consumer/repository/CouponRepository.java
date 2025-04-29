package io.leeseunghee.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.leeseunghee.consumer.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
