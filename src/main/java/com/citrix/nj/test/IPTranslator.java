package com.citrix.nj.test;

import java.io.IOException;
import java.math.BigInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class IPTranslator {

	public static void main(String[] args) throws IOException {
		
		ZipFile file = new ZipFile(IPTranslator.class.getResource("dbip-country-2016-11.zip").getFile());
		ZipEntry entry = file.getEntry("dbip-country-2016-11.csv");
		
		
		 String start = "1.0.64.0";
		 String end = "1.0.127.255";
		 String test = "1.0.69.242";
		 
		 String country = "KR";
		 System.out.println("test:" + ip2int(test).compareTo(ip2int(start)));
		 System.out.println("test:" + ip2int(test).compareTo(ip2int(end)));
	}
	
	public static BigInteger ip2int(String ip){
		StringBuffer sb = new StringBuffer();
		String[] ips = ip.split("\\.");
		for(String s: ips){
			sb.append(org.h2.util.StringUtils.pad(s, 3, "0", false));
		}
		BigInteger big = new BigInteger(sb.toString());
		System.out.println(big);
		return big;
	}

}
