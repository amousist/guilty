package io.github.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.domain.FailedExecution;
import io.github.repositories.FailedExecutionRepository;

@Service
public class FailedExecutionServiceImpl implements FailedExecutionService {
	private FailedExecutionRepository failedExecutionRepository;

	@Autowired
	public void setFailedExecutionRepository(FailedExecutionRepository failedExecutionRepository) {
		this.failedExecutionRepository = failedExecutionRepository;
	}
	
	@Override
	public FailedExecution getFailedExecutionById(Integer id) {
		return failedExecutionRepository.findById(id);
	}

	@Override
	public FailedExecution saveFailedExecution(FailedExecution failedExecution) {
		return failedExecutionRepository.save(failedExecution);
	}

	@Override
	public FailedExecution getLastExecution() {
		return failedExecutionRepository.findFirst1ByOrderByDateDesc();
	}

	@Override
	public Iterable<FailedExecution> getFailedExecutionsInGracePeriod() {
		Instant instant = Instant.now().minus(3, ChronoUnit.HOURS);
		Date gracePeriodStart = Date.from(instant);
		return failedExecutionRepository.findByDateGreaterThanEqual(gracePeriodStart);
	}

	@Override
	public void deleteAll() {
		failedExecutionRepository.deleteAll();
	}

}
