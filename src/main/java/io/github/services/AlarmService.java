package io.github.services;

import org.springframework.scheduling.annotation.Async;

import io.github.domain.JobState;

public interface AlarmService {
	
	public void notify(JobState jobState);
}
