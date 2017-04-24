package io.github.services;

import org.springframework.scheduling.annotation.Async;

public interface AsyncAlarmService {

	@Async
	public void showAlarm(String message);
}
