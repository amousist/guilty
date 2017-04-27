package io.github.services;

import io.github.domain.FailedExecution;

public interface FailedExecutionService {
    FailedExecution getFailedExecutionById(Integer id);
    FailedExecution saveFailedExecution(FailedExecution failedExecution); 
    FailedExecution getLastExecution();
    void deleteAll();
    Iterable<FailedExecution> getFailedExecutionsInGracePeriod();
}
