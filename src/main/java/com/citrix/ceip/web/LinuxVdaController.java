package com.citrix.ceip.web;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.citrix.ceip.CISDataHelper;
import com.citrix.ceip.CISDataRunner;
import com.citrix.ceip.service.linuxvda.LinuxVdaDataService;
import com.citrix.ceip.service.ltsrass.LTSRAssDataService;
import com.citrix.ceip.service.sessionrecording.SRDataService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value="/linuxvda")
public class LinuxVdaController {

	@Autowired
	private LinuxVdaDataService linuxVdaDataService;
	
	@RequestMapping(value={"/",""})
	public String index(Model model) throws IOException{	
				
		model.addAttribute("customerData", linuxVdaDataService.getCustomer());
		model.addAttribute("dailyVdaData", linuxVdaDataService.getDailyVda());
		model.addAttribute("adSolutionData", linuxVdaDataService.getAdSolution());
		model.addAttribute("installTypeData", linuxVdaDataService.getInstallType());
		model.addAttribute("countryData", linuxVdaDataService.getCountry());
		model.addAttribute("osNameData", linuxVdaDataService.getOSName());
		model.addAttribute("lastUpdateTime", linuxVdaDataService.getLastUpdateTime().toString().substring(0,19));
		model.addAttribute("versionData", linuxVdaDataService.getVdaVersion());

		model.addAttribute("receiverTypeData", linuxVdaDataService.getReceiverType());
		
		model.addAttribute("totalCustomer", linuxVdaDataService.getTotalCustomerCount());
		model.addAttribute("totalCount", linuxVdaDataService.getTotalRecord());
		
		//header section highlight
		model.addAttribute("linuxvdaActive", "active");
		return "linuxvda";
	}
	
}
