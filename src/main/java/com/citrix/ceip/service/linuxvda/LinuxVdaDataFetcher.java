package com.citrix.ceip.service.linuxvda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.citrix.ceip.CISDataHelper;
import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.model.linuxvda.Customer;
import com.citrix.ceip.model.linuxvda.PieItem;
import com.citrix.ceip.model.linuxvda.VdaInfo;
import com.citrix.ceip.repository.linuxvda.LinuxVdaCustomerRepository;
import com.citrix.ceip.repository.linuxvda.LinuxVdaInfoRepository;
import com.citrix.ceip.repository.linuxvda.LinuxVdaPieItemRepository;
import com.citrix.ceip.service.AbstractDataFetcher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class LinuxVdaDataFetcher extends AbstractDataFetcher {

	protected static Logger log = LoggerFactory.getLogger(LinuxVdaDataFetcher.class);
	
	@Autowired
	private CISDataHelper cisDataHelper;
	
	@Autowired
	private LinuxVdaCustomerRepository linuxVdaCustomerRepository;
	
	@Autowired
	private LinuxVdaInfoRepository linuxVdaInfoRepository;
	
	@Autowired
	private LinuxVdaPieItemRepository linuxVdaPieItemRepository;
	
	public void getCISData() throws Exception {
		
		//1. get customer list
		String sql = "select max(convert(varchar,ctime,111)) day , max(clientip) ip from linuxvdaceip_uploadinfo where isInternalUpload='false' group by clientip ";
		
		log.info("begin to fetch [linuxvda_customer] ");
		ArrayNode results = cisDataHelper.getAzureSqlResult(sql);

		Iterator<JsonNode> nodeIterator = results.elements();
		log.info("fetch [linuxvda_customer] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
		List<Customer> list = new ArrayList<Customer>();
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			Customer u = new Customer();		
			u.setClientIP(n.get("ip").asText());
			u.setDay(n.get("day").asText());
			u.setCountry(ip2Country.getCountryByIP(u.getClientIP()));			
			list.add(u);
		}		
		linuxVdaCustomerRepository.deleteAll();
		linuxVdaCustomerRepository.save(list);
		log.info("[linuxvda_customer] updated successfully....");
		
		//2. get vdainfo data
		sql = " select max(machine_guid) machine , max(ad_solution) ad , max(update_or_fresh_install) type, max(os_name_version) os, "
			+ " max(vda_version) vda ,max(hdx_3d_pro) hdx3d, max(active_session_number) activeSessionNum, max(convert(varchar,ctime,111)) day "
			+ " from linuxvdaceip_vdaceip_view a, linuxvdaceip_uploadinfo_view b "
			+ " where a.uploaduuid = b.uuid and b.isInternalUpload='false' group by machine_guid  ";
		
		List<VdaInfo> listDashboard = new ArrayList<VdaInfo>();
		
		log.info("begin to fetch [linuxvda_vdainfo] ");
		results = cisDataHelper.getAzureSqlResult(sql);
		nodeIterator = results.elements();
		log.info("fetch [linuxvda_vdainfo] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
	    while(nodeIterator.hasNext()){
	    	try{
	    	JsonNode n = nodeIterator.next();
	    	VdaInfo u = new VdaInfo();
	    	u.setMachineId(n.get("machine").asText());
	    	u.setAdSolution(n.get("ad").asText());
	    	u.setInstallType(n.get("type").asText());
	    	u.setOsName(n.get("os").asText());
	    	u.setDay(n.get("day").asText());
	    	u.setActiveSessionNumber(n.get("activeSessionNum").asInt(0));
	    	u.setHdx3d(n.get("hdx3d").asText(""));
	    	String vdaVersion = n.get("vda").asText();
	    	String[] splitted = vdaVersion.split("\\.", 3);
	    	u.setVersion(splitted[0].replace("xendesktopvda ", "XenDesktopVDA-") + "." + splitted[1]);
	    	listDashboard.add(u);	
	    	}catch(Exception ex){
	    		log.error("error:" + ex);
	    	}
	    }	
	    
	    linuxVdaInfoRepository.deleteAll();
	    linuxVdaInfoRepository.save(listDashboard);
	    log.info("[linuxvda_vdainfo] updated successfully....");
		
		sql = " select receiver_type, count(*) cnt from linuxvdaceip_sessionceip_view group by receiver_type ";		
		log.info("begin to fetch [linuxvda_pie_item] ");
		results = cisDataHelper.getAzureSqlResult(sql);

		nodeIterator = results.elements();
		log.info("fetch [linuxvda_pie_item] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
		List<PieItem> listPieItem = new ArrayList<PieItem>();
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			PieItem u = new PieItem();
			u.setName(n.get("receiver_type").asText("null"));
			u.setCount(n.get("cnt").asInt());
			u.setType("receiver_type");
			listPieItem.add(u);
		}		
		
		linuxVdaPieItemRepository.deleteAll();
		linuxVdaPieItemRepository.save(listPieItem);
		log.info("fetch [linuxvda_pie_item] complete ");
		
	    updateLastUpdateTime(AppNames.LinuxVDA);
	}

	public String getAppName() {
		return AppNames.LinuxVDA;
	}

}
