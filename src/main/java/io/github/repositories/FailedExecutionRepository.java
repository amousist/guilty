package io.github.repositories;

import org.springframework.data.repository.CrudRepository;

import io.github.domain.FailedExecution;

public interface FailedExecutionRepository extends CrudRepository<FailedExecution, Integer>{
	public FailedExecution findById(Integer id);
}
