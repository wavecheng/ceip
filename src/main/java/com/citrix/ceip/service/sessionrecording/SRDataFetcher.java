package com.citrix.ceip.service.sessionrecording;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.citrix.ceip.IP2CountryHelper;
import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.model.sessionrecording.Customer;
import com.citrix.ceip.model.sessionrecording.Recording;
import com.citrix.ceip.repository.sessionrecording.CustomerRepository;
import com.citrix.ceip.repository.sessionrecording.RecordingRepository;
import com.citrix.ceip.service.AbstractDataFetcher;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

public class SRDataFetcher extends AbstractDataFetcher {

	protected static Logger log = LoggerFactory.getLogger(SRDataFetcher.class);
	
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private RecordingRepository recordingRepository;
	
	private IP2CountryHelper ip2Country = new IP2CountryHelper(SRDataFetcher.class.getResource("/ipcountry.txt").getFile());
	
	@Transactional
	public void getCISData() throws Exception {
		
		//1. get customer list
		String sql = " select a.customeruuid , max(substr(b.ctime,1,10)),max(clientip), max(productversion), max(c.ostype) "
				+ " from srtceip.customer a"
				+ " join srtceip.uploadinfo b on a.uploaduuid=b.uuid"
				+ " left join srtceip.os c on  c.uploaduuid =a.uploaduuid "
				+ " group by a.customeruuid ";
		
		log.info("begin to fetch [sr_customer] ");
		ArrayNode results = cisDataHelper.getSqlResult(sql);

		Iterator<JsonNode> nodeIterator = results.elements();
		log.info("fetch [sr_customer] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
		List<Customer> list = new ArrayList<Customer>();
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			Customer u = new Customer();
			u.setId(n.get(0).asText());
			u.setDay(n.get(1).asText());
			u.setClientIP(n.get(2).asText());
			u.setVersion(n.get(3).asText());
			u.setOs(n.get(4).asText("").replaceAll("\"", ""));
			u.setCountry(ip2Country.getCountryByIP(u.getClientIP()));			
			list.add(u);
		}		
		customerRepository.deleteAll();
		customerRepository.save(list);
		log.info("[sr_customer] updated successfully....");
		
		//2. get recoding list
		sql = " select b.uuid, substr(b.ctime,1,10) day , a.recordedagentnum ,  c.recordingnum , d.apprecordingnum "
			+ " from srtceip.recordedagentnum a, srtceip.uploadinfo b , srtceip.recordingnum  c,  srtceip.apprecordingnum d "
			+ " where a.uploaduuid=b.uuid and a.uploaduuid=c.uploaduuid and d.uploaduuid=a.uploaduuid "
			+ " and (a.recordedagentnum > 0 or c.recordingnum > 0 or d.apprecordingnum > 0 ) ";
		
		log.info("begin to fetch [sr_recording] ");
		results = cisDataHelper.getSqlResult(sql);

		nodeIterator = results.elements();
		log.info("fetch [sr_recording] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
		List<Recording> listRecording = new ArrayList<Recording>();
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			Recording u = new Recording();
			u.setUuid(n.get(0).asText());
			u.setDay(n.get(1).asText());
			u.setRecordedAgentNum(n.get(2).asInt());
			u.setRecordingNum(n.get(3).asInt());
			u.setAppRecordingNum(n.get(4).asInt());
			listRecording.add(u);
		}		
		recordingRepository.deleteAll();
		recordingRepository.save(listRecording);
		log.info("[sr_recording] updated successfully....");
		
		updateLastUpdateTime(AppNames.SessionRecording);
		
	}

	public String getAppName() {
		return AppNames.SessionRecording;
	}

}
