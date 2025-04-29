package io.leeseunghee.consumer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.leeseunghee.consumer.domain.FailedEvent;

public interface FailedEventRepository extends JpaRepository<FailedEvent, Long> {
}
