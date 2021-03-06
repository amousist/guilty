package io.github.rest;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.domain.JobState;
import io.github.services.StatusProcesorService;

@RestController
public class StatusReceiver {
	private StatusProcesorService statusProcesorService;

    @Autowired
    public void setStatusProcesorService(StatusProcesorService statusProcesorService) {
        this.statusProcesorService = statusProcesorService;
    }

	@RequestMapping(method=RequestMethod.POST, value="/notify")
	public void postMethod(@RequestBody JobState jobState) throws IOException {
		statusProcesorService.notify(jobState);
	}
	
//	{ "name":"assembla", "url":"job/assembla/", "build":{ "number":4,
//		 "queue_id":22, "phase":"FINALIZED", "status":"FAILURE",
//		 "url":"job/assembla/4/", "scm":{
//		 "url":"https://subversion.assembla.com/svn/cbogRCBtSr5OkLacwqEsg8/trunk",
//		 "commit":"4", "culprits":[ "alek-mou" ] }, "log":"", "artifacts":{
//		 
//		 } } }
}
