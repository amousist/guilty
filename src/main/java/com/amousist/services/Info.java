package com.amousist.services;

import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("info")
public class Info {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String info() throws IOException {
		Alarm.getInstance().loud("Atención! La última compilación falló. El error fue introducido por: Ale Mousist");
		return "Welcome!";
	}
}