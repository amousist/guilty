package io.github.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.domain.FailedExecution;
import io.github.domain.JobState;
import io.github.domain.ScmUser;

@Service
public class StatusProcesorServiceImpl implements StatusProcesorService {

	private ScmUserService scmUserService;
	private FailedExecutionService failedExecutionService;
	private AsyncAlarmService asyncAlarmService;
	private static final Logger logger = LoggerFactory.getLogger(StatusProcesorServiceImpl.class);

	@Override
	public void notify(JobState jobState) {
		switch (jobState.getBuild().getStatus()) {
		case "SUCCESS":
			logger.info("Build successed\n");
			for (FailedExecution failedExecution : failedExecutionService.getFailedExecutionsInGracePeriod()) {
				if (!failedExecution.isMuted()) {
					failedExecution.setMuted(true);
					failedExecutionService.saveFailedExecution(failedExecution);
				}
			}
			break;
		case "FAILURE":
			logger.info("Build failed");
			ArrayList<String> culprits = jobState.getBuild().getScm().getCulprits();
			if (culprits == null || culprits.isEmpty()) {
				asyncAlarmService.showAlarm("Atencion!. La ultima compilacion ha fallado. No se detectaron culpables");
			} else {

				FailedExecution failedExecution = new FailedExecution();
				failedExecution.setBuildNumber(jobState.getBuild().getNumber());
				failedExecution.setCommit(jobState.getBuild().getScm().getCommit());
				failedExecution.setDate(new Date());
				failedExecution.setJobName(jobState.getName());
				failedExecution.setScmUsers(new HashSet<ScmUser>());
				failedExecution.setMuted(false);

				StringBuilder culpritText = new StringBuilder();
				for (int i = 0; i < culprits.size(); i++) {
					String culprit = culprits.get(i);

					ScmUser scmUser = scmUserService.getScmUserByName(culprit);
					if (scmUser == null) {
						scmUser = new ScmUser();
						scmUser.setUsername(culprit);
						scmUser.setFailedExecutions(new HashSet<FailedExecution>());
					}

					failedExecution.getScmUsers().add(scmUser);
					scmUser.getFailedExecutions().add(failedExecution);

					if (i == 0) {
						culpritText.append(culprit);
					} else if (i == (culprits.size() - 1)) {
						culpritText.append(", o, " + culprit);
					} else {
						culpritText.append(", " + culprit);
					}
				}
				failedExecutionService.saveFailedExecution(failedExecution);

				asyncAlarmService.showAlarm("Atencion! La ultima compilacion ha fallado. El error fue introducido por: "
						+ culpritText.toString());

			}
			break;
		}
	}

	@Autowired
	public void setScmUserService(ScmUserService scmUserService) {
		this.scmUserService = scmUserService;
	}

	@Autowired
	public void setFailedExecutionService(FailedExecutionService failedExecutionService) {
		this.failedExecutionService = failedExecutionService;
	}

	@Autowired
	public void setAsyncAlarmService(AsyncAlarmService asyncAlarmService) {
		this.asyncAlarmService = asyncAlarmService;
	}
}
