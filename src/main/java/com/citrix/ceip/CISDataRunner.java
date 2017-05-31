package com.citrix.ceip;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
public class CISDataRunner {

	private static Log logger = LogFactory.getLog(CISDataRunner.class);
	
	private List<DataFetcher> fetchersList;
	
	public CISDataRunner(List<DataFetcher> fetchers) {
		this.fetchersList = fetchers;
	}

	//every day at 22:00 to run data fetcher
	@Scheduled(cron="0 0 22 * * *")
	//@Scheduled(fixedRate=3600*1000)
	public void run(){
		
		logger.info("start to update CIS data...");
		for(DataFetcher df : fetchersList){
			try{
				df.getCISData();
			}catch(Exception ex){
				logger.error("!!!!![" + df.getAppName() + "] update failed!!!!!");
				logger.error(ex);
			}			
		}
		logger.info("updae CIS data finished...");
	}
	
}
