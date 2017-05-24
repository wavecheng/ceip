package com.citrix.ceip.web;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/")
public class HomeController {

	@RequestMapping(value={"/",""})
	public String index(Model model) throws IOException{
		return "index";
	}
	
}
