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
import com.citrix.ceip.service.ltsrass.LTSRAssDataService;
import com.citrix.ceip.service.sessionrecording.SRDataService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value="/ltsrass")
public class LTSRAssController {

	@Autowired
	private LTSRAssDataService ltsrAssDataService;
	
	@RequestMapping(value={"/",""})
	public String index(Model model) throws IOException{	
				
		model.addAttribute("customerData", ltsrAssDataService.getCustomer());
		model.addAttribute("statusData", ltsrAssDataService.getComplianceStatus());
		model.addAttribute("complianceCountData", ltsrAssDataService.getComplianceCount());
		model.addAttribute("countryData", ltsrAssDataService.getCountry());
		model.addAttribute("versionData", ltsrAssDataService.getVersion());
		model.addAttribute("lastUpdateTime", ltsrAssDataService.getLastUpdateTime().toString().substring(0,19));
		model.addAttribute("totalMachines", ltsrAssDataService.getTotalCheckedMachines());
		
		//header section highlight
		model.addAttribute("lstrassActive", "active");
		return "ltsrass";
	}
	
}
