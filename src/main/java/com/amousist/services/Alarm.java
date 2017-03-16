package com.amousist.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class Alarm {
	private static Alarm instance = new Alarm();
	private GpioController gpio;
	private GpioPinDigitalOutput relay;
	private static final Logger LOGGER = Logger.getLogger( Alarm.class.getName() );
	
	private Alarm(){
		gpio = GpioFactory.getInstance();
		relay = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Relay", PinState.LOW);
	}
	
	public static Alarm getInstance(){
		if (instance == null){
			instance = new Alarm();
		}
		return instance;
	}
	
	public synchronized void loud(String message) throws IOException, InterruptedException {
		relay.high();
		
		Thread.sleep(5000);
		
		ArrayList<String> commands = new ArrayList<String>();
		
		commands.add("pico2wave");
		commands.add("-w");
		commands.add("/tmp/voice.wav");
		commands.add("-l");
		commands.add("es-ES");
		commands.add(message);

		Process picoCommand = new ProcessBuilder(commands).start();
		picoCommand.waitFor();
		System.out.println(picoCommand.exitValue());
		System.out.println(picoCommand.getOutputStream());
		System.out.println(picoCommand.getErrorStream());
		
		commands = new ArrayList<String>();
		commands.add("play");
		commands.add("/tmp/voice.wav");

		commands.add("pitch");
		commands.add("-600");

		commands.add("chorus");
		commands.add("0.4");
		commands.add("0.8");
		commands.add("20");
		commands.add("0.5");
		commands.add("0.10");
		commands.add("2");

		commands.add("-t");

		commands.add("echo");
		commands.add("0.9");
		commands.add("0.8");
		commands.add("33");
		commands.add("0.9");

		commands.add("echo");
		commands.add("0.7");
		commands.add("0.7");
		commands.add("10");
		commands.add("0.2");

		commands.add("echo");
		commands.add("0.9");
		commands.add("0.2");
		commands.add("55");
		commands.add("0.5");

		commands.add("gain");
		commands.add("10");

		Process playCommand = new ProcessBuilder(commands).start();
		playCommand.waitFor();
		System.out.println(playCommand.exitValue());
		System.out.println(playCommand.getOutputStream());
		System.out.println(playCommand.getErrorStream());
		
		relay.low();
	}
	
	public void destroy(){
		gpio.shutdown();
		instance = null;
	}
}
