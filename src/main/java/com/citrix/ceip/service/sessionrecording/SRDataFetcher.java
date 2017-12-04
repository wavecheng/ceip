package com.citrix.ceip.service.sessionrecording;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.model.sessionrecording.Customer;
import com.citrix.ceip.model.sessionrecording.PieItem;
import com.citrix.ceip.model.sessionrecording.Recording;
import com.citrix.ceip.repository.sessionrecording.CustomerRepository;
import com.citrix.ceip.repository.sessionrecording.SrPieItemRepository;
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
	@Autowired
	private SrPieItemRepository srPieItemRepository;
	
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
//		sql = " select e.uuid, substr(b.ctime,1,10) day , a.recordedagentnum ,  c.recordingnum , d.apprecordingnum "
//			+ " from srtceip.recordedagentnum a, srtceip.uploadinfo b , srtceip.recordingnum  c,  srtceip.apprecordingnum d  "
//			+ " where a.uploaduuid=b.uuid and a.uploaduuid=c.uploaduuid and d.uploaduuid=a.uploaduuid " 
//		    + " and (a.recordedagentnum > 0 or c.recordingnum > 0 or d.apprecordingnum > 0 ) ";
//		
//		log.info("begin to fetch [sr_recording] ");
//		results = cisDataHelper.getSqlResult(sql);

		//2.1 get monthly recording Agent numbers by customer
		sql = " select distinct b.customeruuid, substring(convert(varchar,c.ctime,111),1,7) month , max(a.recordedagentnum) cnt  "
				+ "from srtceip_recordedagentnum_view a, srtceip_customer_view b , srtceip_uploadinfo_view c "
				+ " where a.uploaduuid=b.uploaduuid and a.recordedagentnum > 0 and a.uploaduuid=c.uuid "
				+ "group by b.customeruuid, substring(convert(varchar,c.ctime,111),1,7) ";
		log.info("begin to fetch [sr_recordedAgentNum] ");
		results = cisDataHelper.getAzureSqlResult(sql);

		nodeIterator = results.elements();
		log.info("fetch [sr_recordedagentnum] complete with total=" + results.size());
		List<Recording> listRecording = new ArrayList<Recording>();
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			Recording u = new Recording();
			u.setUuid(n.get("customeruuid").asText());
			u.setDay(n.get("month").asText());
			u.setRecordedAgentNum(n.get("cnt").asInt());
			listRecording.add(u);
		}		
		
		//2.2 get recording num
		sql = " select distinct b.customeruuid, substring(convert(varchar,c.ctime,111),1,7) month , max(a.recordingnum) cnt  "
				+ "from srtceip_recordingnum_view a, srtceip_customer_view b , srtceip_uploadinfo_view c "
				+ " where a.uploaduuid=b.uploaduuid and a.recordingnum > 0 and a.uploaduuid=c.uuid "
				+ "group by b.customeruuid, substring(convert(varchar,c.ctime,111),1,7) ";
		log.info("begin to fetch [sr_recordingnum] ");
		results = cisDataHelper.getAzureSqlResult(sql);
		nodeIterator = results.elements();
		log.info("fetch [sr_recordingnum] complete with total=" + results.size());
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			String uuid = n.get("customeruuid").asText();
			String month = n.get("month").asText();
			Recording u = findExistingRecord(listRecording,uuid,month);
			if(u != null){
				//listRecording.remove(u);
				u.setRecordingNum(n.get("cnt").asInt());
			}
			else{
				u = new Recording();
				u.setUuid(n.get("customeruuid").asText());
				u.setDay(n.get("month").asText());
				u.setRecordingNum(n.get("cnt").asInt());
				listRecording.add(u);
			}		
		}	

		//2.3 get apprecording num
		sql = " select distinct b.customeruuid, substring(convert(varchar,c.ctime,111),1,7) month , max(a.apprecordingnum) cnt  "
				+ "from srtceip_apprecordingnum_view a, srtceip_customer_view b , srtceip_uploadinfo_view c "
				+ " where a.uploaduuid=b.uploaduuid and a.apprecordingnum > 0 and a.uploaduuid=c.uuid "
				+ "group by b.customeruuid, substring(convert(varchar,c.ctime,111),1,7) ";
		log.info("begin to fetch [sr_apprecordingnum] ");
		results = cisDataHelper.getAzureSqlResult(sql);
		nodeIterator = results.elements();
		log.info("fetch [sr_apprecordingnum] complete with total=" + results.size());
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			String uuid = n.get("customeruuid").asText();
			String month = n.get("month").asText();
			Recording u = findExistingRecord(listRecording,uuid,month);
			if(u != null){
				//listRecording.remove(u);
				u.setAppRecordingNum(n.get("cnt").asInt());
			}
			else{
				u = new Recording();
				u.setUuid(n.get("customeruuid").asText());
				u.setDay(n.get("month").asText());
				u.setAppRecordingNum(n.get("cnt").asInt());
				listRecording.add(u);
			}
			
		}
		
		recordingRepository.deleteAll();
		recordingRepository.save(listRecording);
		log.info("saving sr_recording...." + listRecording.size());
		
		//3.1 get loadbalancingstatus 
		log.info("begin to fetch [sr_pieitem] ");	
		sql = "select loadbalancingstatus, count(*) as cnt from ( " 
				+ " select distinct b.customeruuid, a.loadbalancingstatus from srtceip_loadbalanceenable_view a , srtceip_customer_view b where a.uploaduuid = b.uploaduuid "
				+ " ) t group by loadbalancingstatus ";
		log.info("begin to fetch [sr_loadbalancingstatus] ");
		results = cisDataHelper.getAzureSqlResult(sql);

		nodeIterator = results.elements();
		log.info("fetch [sr_loadbalancingstatus] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
		
		List<PieItem> listPieItem = new ArrayList<PieItem>();
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			PieItem u = new PieItem();
			u.setName(n.get("loadbalancingstatus").asText());
			u.setCount(n.get("cnt").asInt());
			u.setType("loadbalancing_status");
			listPieItem.add(u);
		}		
		
		//3.2 get adminloggingstatus	
		sql = "select adminloggingstatus, count(*) as cnt from ( " 
				+ " select distinct b.customeruuid, a.adminloggingstatus from srtceip_adminlogenable_view a , srtceip_customer_view b where a.uploaduuid = b.uploaduuid "
				+ " ) t group by adminloggingstatus ";
		log.info("begin to fetch [sr_adminloggingstatus] ");
		results = cisDataHelper.getAzureSqlResult(sql);

		nodeIterator = results.elements();
		log.info("fetch [sr_adminloggingstatus] complete with total=" + results.size());
		if(results.size() == 0) 
			return;
				
		while(nodeIterator.hasNext()){
			JsonNode n = nodeIterator.next();
			PieItem u = new PieItem();
			u.setName(n.get("adminloggingstatus").asText());
			u.setCount(n.get("cnt").asInt());
			u.setType("adminlogging_status");
			listPieItem.add(u);
		}		
		
		srPieItemRepository.deleteAll();
		srPieItemRepository.save(listPieItem);
		log.info("[sr_pieitem] updated successfully....");

		updateLastUpdateTime(AppNames.SessionRecording);
	}

	public String getAppName() {
		return AppNames.SessionRecording;
	}

	private Recording findExistingRecord(List<Recording> list, String uuid, String month){
		for(Recording r:list){
			if(r.getUuid().compareToIgnoreCase(uuid) == 0 && r.getDay().compareToIgnoreCase(month) == 0)
				return r;
		}
		return null;
	}
}
