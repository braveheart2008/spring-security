package org.snail.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SnailController {
	
	@RequestMapping("/hi")
	public String getVersion(){
		return "Spring boot 2.2.5.RELEASE";
	}
	
	@RequestMapping("/leader")
	public String leader(){
		return "The leader home page.";
	}
	
	@RequestMapping("/manager")
	public String manager(){
		return "The manager home page.";
	}

}
