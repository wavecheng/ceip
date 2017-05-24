package com.citrix.ceip;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IP2CountryHelper {

	protected static Logger log = LoggerFactory.getLogger(IP2CountryHelper.class);
	
	static class IPCountry{
		BigInteger start;
		BigInteger end;
		String country = "other";
	}
	
	private static List<IPCountry> list = new LinkedList<IPCountry>();
	
	public static BigInteger ip2int(String ip){
		if(ip == null || ip.length() == 0)
			return new BigInteger("0");
		
		StringBuffer sb = new StringBuffer();
		String[] ips = ip.split("\\.");
		for(String s: ips){
			sb.append(org.h2.util.StringUtils.pad(s, 3, "0", false));
		}
		BigInteger big = new BigInteger(sb.toString());
		return big;
	}
	
	public IP2CountryHelper(String file){	
		try{
			LineIterator iterator = FileUtils.lineIterator(new File(file));
			while(iterator.hasNext()){
				String line = iterator.nextLine();
				String[] parts = line.split(":");
				IPCountry o = new IPCountry();
				o.country = parts[2];
				o.start = new BigInteger(parts[0]);
				o.end = new BigInteger(parts[1]);			
				list.add(o);
			}		
		}catch(Exception ex){
			log.error("can't load country table: " + ex.getMessage());
		}
	}
	
	public String getCountryByIP(String ip){
		BigInteger input = ip2int(ip);	
		for(IPCountry o : list){
			if(input.compareTo(o.start) >=0 && input.compareTo(o.end) <= 0)
				return o.country;
		}
		
		return "other";
	}
	
	
	public static void main(String[] args) throws IOException {
		IP2CountryHelper helper = new IP2CountryHelper(IP2CountryHelper.class.getResource("/ipcountry.txt").getFile());
		System.out.println(helper.getCountryByIP("103.14.252.251"));		
	}
}
