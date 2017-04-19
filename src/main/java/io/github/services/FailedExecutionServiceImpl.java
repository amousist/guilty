package io.github.services;

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

}
