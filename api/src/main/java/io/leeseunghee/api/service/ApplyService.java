package io.leeseunghee.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.leeseunghee.api.producer.CouponCreateProducer;
import io.leeseunghee.api.respository.AppliedUserRepository;
import io.leeseunghee.api.respository.CouponCountRepository;
import io.leeseunghee.api.respository.CouponRepository;

@Service
public class ApplyService {

	private final CouponRepository couponRepository;
	private final CouponCountRepository couponCountRepository;
	private final CouponCreateProducer couponCreateProducer;
	private final AppliedUserRepository appliedUserRepository;

	public ApplyService(
		CouponRepository couponRepository,
		CouponCountRepository couponCountRepository,
		CouponCreateProducer couponCreateProducer,
		AppliedUserRepository appliedUserRepository
	) {
		this.couponRepository = couponRepository;
		this.couponCountRepository = couponCountRepository;
		this.couponCreateProducer = couponCreateProducer;
		this.appliedUserRepository = appliedUserRepository;
	}

	@Transactional
	public void apply(Long userId) {
		Long apply = appliedUserRepository.add(userId);

		// 이미 발급된 경우
		if (apply != 1) {
			return;
		}

		Long count = couponCountRepository.increment();

		if (count > 100) {
			return;
		}

		couponCreateProducer.create(userId);
	}

}
