package com.citrix.nj.test;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class CISHelper {
	
	private static String baseUrl = "https://cis.citrix.com"; // "https://cis.citrix.com"; //prod    
	
	public static void main(String[] args) throws IOException{
		
//		String vdaVersion = "XenDesktopVDA-7.13.0.382-1.el7_2.x86_64";
//    	String[] splitted = vdaVersion.split("\\.", 3);
//    	System.out.println(splitted[0] + "." + splitted[1]);
//    	
    	
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username", "boch");
		param.put("password", "Sin9300=0"); //XXX: change before run
		
		HttpEntity entity = new HttpEntity(param, headers);
		
		//1. identity token
		String url = baseUrl + "/auth/api/create_identity/";

		ResponseEntity<Token> response = restTemplate.exchange(url, HttpMethod.POST, entity, Token.class);	
		String idToken = response.getBody().getToken();
		System.out.println("identity token: " + idToken);
		
		//2. grant token:
		headers.add(HttpHeaders.AUTHORIZATION, "BT " + idToken);
		url = baseUrl + "/dw/api/create_grant/";
		param.clear();
		param.put("max_age", 7200);
		entity = new HttpEntity(param, headers);
		response = restTemplate.exchange(url, HttpMethod.POST, entity, Token.class);	
		String grantToken = response.getBody().getToken();
		System.out.println("grant token: " + grantToken);
		
		//3.Retrieve data access token
		headers.set(HttpHeaders.AUTHORIZATION, "BT " + grantToken);
		url = baseUrl + "/dw/api/grant_access/";
		param.clear();
		param.put("max_age", 7200);
		entity = new HttpEntity(param, headers);
		response = restTemplate.exchange(url, HttpMethod.POST, entity, Token.class);	
		String accessToken = response.getBody().getToken();
		System.out.println("access token: " + accessToken);		
		
		//4.retrieve data
		headers.set(HttpHeaders.AUTHORIZATION, "BT " + accessToken);
		url = baseUrl + "/dw/api/sql?q=select uid, max(osname),max(upmversion),max(serviceactive),max(localprofileconflicthandling),max(migratewindowsprofilestouserstore) from upm.user_profile_management group by uid ";
		url = baseUrl + "/dw/api/describe?db=srtceip&table=loadbalanceenable";
		
		url = baseUrl + "/dw/api/sql?q=select substr(b.ctime,1,10) day , a.customizedpoliciesnum "
				+ " from srtceip.customizedpn a, srtceip.uploadinfo b "
				+ " where a.uploaduuid=b.uuid";
		url = baseUrl + "/dw/api/sql?q=select loadbalancingstatus, count(*) cnt from srtceip.loadbalanceenable group by loadbalancingstatus";
		
//		url = baseUrl + "/dw/api/sql?q=select max(substr(b.ctime,1,10)),max(clientip), max(productversion),max(c.ostype) "
//				+ " from srtceip.customer a"
//				+ " join srtceip.uploadinfo b on a.uploaduuid=b.uuid"
//				+ " left join srtceip.os c on  c.uploaduuid =a.uploaduuid "
//				+ " group by a.customeruuid ";
		
		//1.legacy sql
		//url = baseUrl + "/dw/api/sql?q=select a.* from ltsrass.summary a, (select clientip, max(uuid) uuid from ltsrass.uploadinfo group by clientip) b where a.uploaduuid=b.uuid " ; //sql?q=select count(*) from ltsrass.uploadinfo";
		//url = baseUrl + "/dw/api/sql?q=select * from linuxvdaceip.uploadinfo  limit 5 "
		//		+ "  "; //where uploaduuid='532d22b7-8fc9-416b-bfd3-4527844c3c43' ";
		entity = new HttpEntity(param, headers);
		ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		System.out.println(data.getBody());
		
		//param.put("sql", "  select max(convert(varchar,ctime,111)) day , max(clientip) id from linuxvdaceip_uploadinfo where isInternalUpload='false' group by clientip  ");
/*
		String sql = " select max(machine_guid) machine , max(ad_solution) ad , max(update_or_fresh_install) type, max(os_name_version) os, max(vda_version) vda "
				+ " from linuxvdaceip_vdaceip_view  group by machine_guid  ";
		
		sql = " select os,vda,parameter, avg(datediff(ss, starttime, endtime)) secSpend, count(*) cnt  from vdacleanup_metadata_view group by os,vda,parameter ";
		
		sql = "select max(convert(varchar,ctime,111)) day , max(clientip) ip, max(clienttoolversion) clientToolVersion from vdacleanup_uploadinfo where isInternalUpload='false' group by clientip" ;
		
		//2. sql-azure
		url = baseUrl + "/dw/api/sql-azure/"; 
		param.clear();
		param.put("timeout", 300000);
		param.put("sql", sql);
		entity = new HttpEntity(param, headers);
		
		ResponseEntity<String> data  = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		FileWriter fw = new FileWriter("c:/test.csv");
		fw.write(data.getBody());
		fw.close();
		System.out.println("output c:/test.csv finish" ); //data.getBody());	
		*/	
	}
	
}
