package com.citrix.ceip.service;

import java.sql.Timestamp;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.citrix.ceip.CISDataHelper;
import com.citrix.ceip.DataFetcher;
import com.citrix.ceip.IP2CountryHelper;
import com.citrix.ceip.model.LastUpdateTime;
import com.citrix.ceip.repository.LastUpdateTimeRepository;

public abstract class AbstractDataFetcher implements DataFetcher {

	protected static Logger log = LoggerFactory.getLogger(AbstractDataFetcher.class);
	
	@Autowired
	protected CISDataHelper cisDataHelper;
	
	@Autowired
	private LastUpdateTimeRepository lastUpdateTimeRepository;
	
	protected IP2CountryHelper ip2Country = new IP2CountryHelper(AbstractDataFetcher.class.getResource("/ipcountry.txt").getFile());
	
	
	protected void updateLastUpdateTime(String appName){
	    Timestamp now = Timestamp.from(Calendar.getInstance().getTime().toInstant());
	    LastUpdateTime last = lastUpdateTimeRepository.findOne(appName);
		if(last == null){
			last = new LastUpdateTime();
			last.setAppName(appName);
		}
		last.setLastUpdateTime(now);
		lastUpdateTimeRepository.save(last);
	}
}
