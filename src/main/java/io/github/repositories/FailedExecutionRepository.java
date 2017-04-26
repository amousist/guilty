package io.github.repositories;

import java.util.Date;
import org.springframework.data.repository.CrudRepository;

import io.github.domain.FailedExecution;

public interface FailedExecutionRepository extends CrudRepository<FailedExecution, Integer>{
	public FailedExecution findById(Integer id);
	public FailedExecution findFirst1ByOrderByDateDesc();
	public Iterable<FailedExecution> findByDateGreaterThanEqual(Date date);
}
