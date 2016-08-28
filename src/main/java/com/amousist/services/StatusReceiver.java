package com.amousist.services;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("notify")
public class StatusReceiver {

@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_HTML)
public String postMethod(String mensaje) {
	System.out.println(mensaje);
	return "OK";
}
}