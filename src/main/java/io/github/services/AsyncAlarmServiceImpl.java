package io.github.services;

import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

@Service
public class AsyncAlarmServiceImpl implements AsyncAlarmService {

	private static final Logger logger = LoggerFactory.getLogger(AsyncAlarmServiceImpl.class);
	private GpioController gpio;
	private GpioPinDigitalOutput relay;

	@PostConstruct
	private void initiate() {
		logger.info("Initializing GPIO");
		gpio = GpioFactory.getInstance();
		relay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Relay", PinState.LOW);
	}

	@Override
	public void showAlarm(String message) {
		relay.high();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		speak(message);
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
		 gpio.shutdown();
	}
}
