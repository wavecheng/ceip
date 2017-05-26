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
import com.citrix.ceip.service.sessionrecording.SRDataService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value="/ltsrass")
public class LTSRAssController {

	@Autowired
	private SRDataService srDataService;
	
	@RequestMapping(value={"/",""})
	public String index(Model model) throws IOException{	
		
		
//		model.addAttribute("customerData", srDataService.getCustomer());
//		model.addAttribute("osData", srDataService.getOS());
//		model.addAttribute("recordingTypeData", srDataService.getRecodingNumPerMonth());
//		model.addAttribute("deploySizeData", srDataService.getAgentSize());
//		model.addAttribute("countryData", srDataService.getCountry());
//		model.addAttribute("versionData", srDataService.getVersion());
//		model.addAttribute("lastUpdateTime", srDataService.getLastUpdateTime().toString().substring(0,19));
		
		//header section highlight
		model.addAttribute("lstrassActive", "active");
		return "ltsrass";
	}
	
}
