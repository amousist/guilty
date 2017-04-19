package io.github.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import io.github.services.ScmUserService;

@Controller
public class ScmUserController {

	private ScmUserService scmUserService;

	@Autowired
	public void setScmUserService(ScmUserService scmUserService) {
		this.scmUserService = scmUserService;
	}

	@RequestMapping(value = "/hall-of-blame", method = RequestMethod.GET)
	public String list(Model model) {
		model.addAttribute("scmUsers", scmUserService.listAllScmUsers());
		return "hall-of-blame";
	}
}
