package io.leeseunghee.api.service;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.leeseunghee.api.respository.CouponCountRepository;
import io.leeseunghee.api.respository.CouponRepository;

@SpringBootTest
class ApplyServiceTest {

	@Autowired
	private ApplyService applyService;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private CouponCountRepository couponCountRepository;

	@Test
	void 한번만_응모() {
	    // given -- 테스트의 상태 설정

	    // when -- 테스트하고자 하는 행동
		applyService.apply(1L);

	    long count = couponRepository.count();

	    // then -- 예상되는 변화 및 결과
		assertThat(count).isEqualTo(1);
	}

	@Test
	void 여러명_응모() throws InterruptedException {
	    // given -- 테스트의 상태 설정
	    int threadCount = 1000;
		ExecutorService executorService = Executors.newFixedThreadPool(32);
		CountDownLatch latch = new CountDownLatch(threadCount);

		// when -- 테스트하고자 하는 행동
		for (int i = 0; i < threadCount; i++) {
			long userId = i;
			executorService.submit(() -> {
				try {
					applyService.apply(userId);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();
		long count = couponRepository.count();

	    // then -- 예상되는 변화 및 결과
		assertThat(count).isEqualTo(100);
	}
}