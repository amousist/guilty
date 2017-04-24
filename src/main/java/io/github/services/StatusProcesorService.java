package io.github.services;

import io.github.domain.JobState;

public interface StatusProcesorService {
	
	public void notify(JobState jobState);
}
