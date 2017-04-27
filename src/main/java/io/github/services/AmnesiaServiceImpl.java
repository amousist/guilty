package io.github.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AmnesiaServiceImpl implements AmnesiaService {
	private FailedExecutionService failedExecutionService;
	private static final Logger logger = LoggerFactory.getLogger(AmnesiaServiceImpl.class);
	
	@Scheduled(cron="0 * * * * *")
	public void forgetKnowledge() {
		logger.info("Performing cleanup...");
		this.failedExecutionService.deleteAll();
	}

	@Autowired
	public void setFailedExecutionService(FailedExecutionService failedExecutionService) {
		this.failedExecutionService = failedExecutionService;
	}

}
