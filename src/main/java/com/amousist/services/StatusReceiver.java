package com.amousist.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.amousist.model.JobState;
import com.google.gson.Gson;

@Path("notify")
public class StatusReceiver {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public String postMethod(String mensaje) throws IOException {
		System.out.println(mensaje);

		Gson gson = new Gson();
		JobState jobState = gson.fromJson(mensaje, JobState.class);

		switch (jobState.getBuild().getStatus()) {
		case "SUCCESS":
			System.out.println("Build successed\n");
			break;
		case "FAILURE":
			System.out.println("Build failed");
			ArrayList<String> culprits = jobState.getBuild().getScm().getCulprits();
			if (culprits == null || culprits.isEmpty()) {
				Alarm.getInstance().loud("Atención! La última compilación falló. No se detectaron culpables");
			} else {
				StringBuilder culpritText = new StringBuilder();
				for (int i = 0; i < culprits.size(); i++) {
					String culprit = culprits.get(i);
					if (i == 0) {
						culpritText.append(culprit);
					} else if (i == (culprits.size() - 1)) {
						culpritText.append(", o, " + culprit);
					} else {
						culpritText.append(", " + culprit);
					}
				}
				Alarm.getInstance().loud("Atención! La última compilación falló. El error fue introducido por: "
						+ culpritText.toString());
			}
			break;
		}

		return "OK";
	}
}

/*
 * { "name":"assembla", "url":"job/assembla/", "build":{ "number":4,
 * "queue_id":22, "phase":"FINALIZED", "status":"FAILURE",
 * "url":"job/assembla/4/", "scm":{
 * "url":"https://subversion.assembla.com/svn/cbogRCBtSr5OkLacwqEsg8/trunk",
 * "commit":"4", "culprits":[ "alek-mou" ] }, "log":"", "artifacts":{
 * 
 * } } }
 */