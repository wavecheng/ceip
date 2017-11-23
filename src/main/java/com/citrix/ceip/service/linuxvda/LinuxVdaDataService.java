package com.citrix.ceip.service.linuxvda;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.citrix.ceip.model.AppNames;
import com.citrix.ceip.service.AbstractDataService;

@Service
public class LinuxVdaDataService extends AbstractDataService {

	public List<Map> getCustomer(){
		String sql = " SELECT date(day) e_date , (SELECT  COUNT(DISTINCT clientip) FROM linuxvda_customer  WHERE DATE(day) <= e_date ) as cnt "
					+ " FROM linuxvda_customer AS e GROUP BY e_date order by e_date " ;	
		return doQuery(sql);		
	}

	public List<Map> getDailyVda(){
		String sql = " SELECT date(day) e_date , (SELECT  COUNT(DISTINCT machineid) FROM linuxvda_vda  WHERE DATE(day) <= e_date ) as cnt "
					+ " FROM linuxvda_vda AS e GROUP BY e_date order by e_date " ;	
		return doQuery(sql);		
	}
	
	
	public List<Map> getMonthlyActiveSessionCount(){
		String sql = " SELECT substr(name,1,7) month, sum(count) cnt FROM linuxvda_pie_item where type='daily_session'  GROUP BY month order by month " ;	
		return doQuery(sql);
	}
	public List<Map> getCountry(){
		String sql = " SELECT country, count(*) cnt FROM linuxvda_customer group by country order by cnt desc  " ;	
		return doQuery(sql);		
	}	

	public List<Map> getAdSolution(){
		String sql = " SELECT adSolution, count(*) cnt FROM linuxvda_vda group by adSolution order by cnt desc  " ;	
		return doQuery(sql);		
	}	
	
	public List<Map> getHdx3dPro(){
		String sql = " SELECT hdx3d, count(*) cnt FROM linuxvda_vda group by hdx3d order by cnt desc  " ;	
		return doQuery(sql);		
	}	

	public List<Map> getVdiMode(){
		String sql = " SELECT vdiMode, count(*) cnt FROM linuxvda_vda group by vdiMode order by cnt desc  " ;	
		return doQuery(sql);		
	}	
	
	public List<Map> getInstallType(){
		String sql = " SELECT installType, count(*) cnt FROM linuxvda_vda group by installType order by cnt desc  " ;	
		return doQuery(sql);		
	}	
		
	public List<Map> getOSName(){
		String sql = " SELECT osName, count(*) cnt FROM linuxvda_vda group by osName order by cnt desc  " ;	
		return doQuery(sql);		
	}	

	public List<Map> getVdaVersion(){
		String sql = " SELECT version, count(*) cnt FROM linuxvda_vda group by version order by cnt desc  " ;	
		return doQuery(sql);		
	}	
	
	public List<Map> getReceiverType(){
		String sql = " SELECT name, count as cnt FROM linuxvda_pie_item where type='receiver_type' " ;	
		Map<String,Object> substitute = new HashMap<String,Object>();
		substitute.put("82", "Mac");
		substitute.put("1", "Windows");
		substitute.put("257", "Chrome/HTML5");
		substitute.put("81", "Linux");
		substitute.put("83", "iOS");
		substitute.put("84", "Android");
		substitute.put("null", "N/A");
		return doQuery(sql,substitute);		
	}
	
	@Override
	public Timestamp getLastUpdateTime() {
		return getLastUpdateTime(AppNames.LinuxVDA);
	}

	@Override
	public int getTotalRecord() {
		return getRecordCount("linuxvda_vda");
	}

	public int getTotalCustomerCount(){
		return getRecordCount("linuxvda_customer");
	}
}
