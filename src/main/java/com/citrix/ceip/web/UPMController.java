package com.citrix.ceip.web;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.citrix.ceip.service.upm.UPMDataService;

@Controller
@RequestMapping("/upm")
public class UPMController {

	@Autowired
	private UPMDataService uPMDataService;
	
	@RequestMapping({"/", ""})
	public String index(Model model) throws IOException{
		model.addAttribute("customerData", uPMDataService.getCustomer());
		model.addAttribute("countryData",uPMDataService.getCountry());
		model.addAttribute("osname", uPMDataService.getOSName());
		model.addAttribute("version", uPMDataService.getVersion());
		model.addAttribute("service", uPMDataService.getServiceActive());
		model.addAttribute("migrateProfile", uPMDataService.getMigrateProfile());
		model.addAttribute("profileHandling", uPMDataService.getProfileHandling());
		model.addAttribute("lastUpdateTime", uPMDataService.getLastUpdateTime().toString().substring(0, 19));
		model.addAttribute("totalCount", uPMDataService.getTotalRecord());
		
		//header section highlight
		model.addAttribute("upmActive", "active");
		
		return "upm";
	}
}
