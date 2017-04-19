package io.github.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

import io.github.domain.FailedExecution;
import io.github.domain.JobState;
import io.github.domain.ScmUser;

@Service
public class AlarmServiceImpl implements AlarmService {

	 private GpioController gpio;
	 private GpioPinDigitalOutput relay;
	private ScmUserService scmUserService;
	private FailedExecutionService failedExecutionService;
	private static final Logger logger = LoggerFactory.getLogger(AlarmServiceImpl.class);

	@PostConstruct
	private void initiate() {
		logger.info("Initializing GPIO");
		 gpio = GpioFactory.getInstance();
		 relay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Relay",
		 PinState.LOW);
	}

	@Override
	public void notify(JobState jobState) {
		switch (jobState.getBuild().getStatus()) {
		case "SUCCESS":
			logger.info("Build successed\n");
			break;
		case "FAILURE":
			logger.info("Build failed");
			ArrayList<String> culprits = jobState.getBuild().getScm().getCulprits();
			if (culprits == null || culprits.isEmpty()) {
				 showAlarm("Atencion!. La ultima compilacion ha fallado. No se detectaron culpables");
			} else {

				FailedExecution failedExecution = new FailedExecution();
				failedExecution.setBuildNumber(jobState.getBuild().getNumber());
				failedExecution.setCommit(jobState.getBuild().getScm().getCommit());
				failedExecution.setDate(new Date());
				failedExecution.setJobName(jobState.getName());
				failedExecution.setScmUsers(new HashSet<ScmUser>());

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

				this.showAlarm("Atencion! La ultima compilacion ha fallado. El error fue introducido por: "
						+ culpritText.toString());

			}
			break;
		}
	}

	public synchronized void showAlarm(String message) {
		 relay.high();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		speak(message);
		 relay.low();
	}

	public synchronized void speak(String message) {

		try {
			ArrayList<String> picoArgs = new ArrayList<String>();
			picoArgs.add("pico2wave");
			picoArgs.add("-w");
			picoArgs.add("/tmp/voice.wav");
			picoArgs.add("-l");
			picoArgs.add("es-ES");
			picoArgs.add(message);

			ArrayList<String> playArgs = new ArrayList<String>();
			playArgs.add("play");
			playArgs.add("/tmp/voice.wav");

			playArgs.add("pitch");
			playArgs.add("-600");

			playArgs.add("chorus");
			playArgs.add("0.4");
			playArgs.add("0.8");
			playArgs.add("20");
			playArgs.add("0.5");
			playArgs.add("0.10");
			playArgs.add("2");

			playArgs.add("-t");

			playArgs.add("echo");
			playArgs.add("0.9");
			playArgs.add("0.8");
			playArgs.add("33");
			playArgs.add("0.9");

			playArgs.add("echo");
			playArgs.add("0.7");
			playArgs.add("0.7");
			playArgs.add("10");
			playArgs.add("0.2");

			playArgs.add("echo");
			playArgs.add("0.9");
			playArgs.add("0.2");
			playArgs.add("55");
			playArgs.add("0.5");

			playArgs.add("gain");
			playArgs.add("10");

			Process picoCommand = new ProcessBuilder(picoArgs).start();
			picoCommand.waitFor();

			Process playCommand = new ProcessBuilder(playArgs).start();
			playCommand.waitFor();

		} catch (IOException | InterruptedException e) {
			logger.error(e.getMessage());
		}

	}

	@PreDestroy
	public void destroy() {
		logger.info("Shutting down GPIO");
		// gpio.shutdown();
	}

	@Autowired
	public void setScmUserService(ScmUserService scmUserService) {
		this.scmUserService = scmUserService;
	}

	@Autowired
	public void setFailedExecutionService(FailedExecutionService failedExecutionService) {
		this.failedExecutionService = failedExecutionService;
	}

}
