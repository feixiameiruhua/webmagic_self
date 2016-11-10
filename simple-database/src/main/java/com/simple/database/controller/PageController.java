package com.simple.database.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/")
@Controller
public class PageController {

	/**
	 * 页面跳转通用方法
	 * @param pageName
	 * @return
	 */
	@RequestMapping(value="{pageName}", method = RequestMethod.GET)
	public String toPage(@PathVariable("pageName")String pageName){
		return pageName;
	}
	
}
