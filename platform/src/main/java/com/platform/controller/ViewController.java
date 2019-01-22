package com.platform.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 视图
 * @author wuyudong
 *
 */
@Controller
public class ViewController {

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginGet(){
		return "login";
	}


	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String register(){
		return "register";
	}

	@RequestMapping("/index")
	public String index() {
 		return "index";
	}



	@RequestMapping("/user/manager")
	public String user() {
		return "user/manager";
	}

	@RequestMapping("/address/manager")
	public String address() {
		return "system/address/address";
	}

	@RequestMapping("/event/manager")
	public String event() {
		return "event/manager";
	}


	@RequestMapping("/count/managerDay")
	public String managerDay() {
		return "count/managerDay";
	}


	@RequestMapping("/count/managerYear")
	public String managerYear() {
		return "count/managerYear";
	}

	@RequestMapping("/count/managerExcel")
	public String managerExcel() {
		return "count/managerExcel";
	}



	@RequestMapping("/event/manager_reduce")
	public String manager_reduce() {
		return "event/manager_reduce";
	}

}
