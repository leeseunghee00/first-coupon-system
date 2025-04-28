package io.leeseunghee.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.leeseunghee.api.domain.Coupon;
import io.leeseunghee.api.respository.CouponCountRepository;
import io.leeseunghee.api.respository.CouponRepository;

@Service
public class ApplyService {

	private final CouponRepository couponRepository;
	private final CouponCountRepository couponCountRepository;

	public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository) {
		this.couponRepository = couponRepository;
		this.couponCountRepository = couponCountRepository;
	}

	@Transactional
	public void apply(Long userId) {
		Long count = couponCountRepository.increment();

		if (count > 100) {
			return;
		}

		couponRepository.save(new Coupon(userId));
	}

}
