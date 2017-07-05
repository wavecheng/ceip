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
import com.citrix.ceip.service.vdacleanup.VdaCleanupDataService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(value="/vdacleanup")
public class VdaCleanupContoller {

	@Autowired
	private VdaCleanupDataService vdaCleanupDataService;
	
	@RequestMapping(value={"/",""})
	public String index(Model model) throws IOException{	
				
		model.addAttribute("customerData", vdaCleanupDataService.getCustomer());
		model.addAttribute("rebootTimesData", vdaCleanupDataService.getRebootTimes());
		model.addAttribute("runTypeData", vdaCleanupDataService.getRunType());
		model.addAttribute("countryData", vdaCleanupDataService.getCountry());
		model.addAttribute("osCleanTimeData", vdaCleanupDataService.getOsCleanTime());
		model.addAttribute("lastUpdateTime", vdaCleanupDataService.getLastUpdateTime().toString().substring(0,19));
		model.addAttribute("vdaCleanTimeData", vdaCleanupDataService.getVdaVersionCleanTime());
		model.addAttribute("vcuVersionData", vdaCleanupDataService.getVcuVersion());
		model.addAttribute("uninstallType", vdaCleanupDataService.getUninstallType());
		model.addAttribute("uninstallTopFile", vdaCleanupDataService.getUninstallTopFile());
		model.addAttribute("uninstallStatus", vdaCleanupDataService.getUninstallStatus());
		model.addAttribute("uninstallFailedTopFile", vdaCleanupDataService.getUninstallFailedTopFile());
		model.addAttribute("uninstallFailedErrorId", vdaCleanupDataService.getUninstallFailedErrorId());
		
		model.addAttribute("totalCustomer", vdaCleanupDataService.getTotalCustomerCount());
		model.addAttribute("totalCount", vdaCleanupDataService.getTotalRecord());
		
		List a = vdaCleanupDataService.getUninstallTopFile();
		//header section highlight
		model.addAttribute("vdaCleanupActive", "active");
		return "vdacleanup";
	}
	
}
