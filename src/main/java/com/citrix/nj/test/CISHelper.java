package com.citrix.nj.test;

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
	
	private static String baseUrl = "https://cis.citrite.net";
	
	public static void main(String[] args) throws IOException{
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username", "boch");
		param.put("password", "*******"); //XXX: change before run
		
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
//		url = baseUrl + "/dw/api/sql?q=select uid, max(osname),max(upmversion),max(serviceactive),max(localprofileconflicthandling),max(migratewindowsprofilestouserstore) from upm.user_profile_management group by uid ";
	
//		url = baseUrl + "/dw/api/sql?q=select b.uuid, substr(b.ctime,1,10) day , a.recordedagentnum ,  c.recordingnum , d.apprecordingnum "
//				+ " from srtceip.recordedagentnum a, srtceip.uploadinfo b , srtceip.recordingnum  c,  srtceip.apprecordingnum d "
//				+ " where a.uploaduuid=b.uuid and a.uploaduuid=c.uploaduuid and d.uploaduuid=a.uploaduuid "
//				+ " and (a.recordedagentnum > 0 or c.recordingnum > 0 or d.apprecordingnum > 0 ) ";
		
		url = baseUrl + "/dw/api/sql?q=select max(substr(b.ctime,1,10)),max(clientip), max(productversion),max(c.ostype) "
				+ " from srtceip.customer a"
				+ " join srtceip.uploadinfo b on a.uploaduuid=b.uuid"
				+ " left join srtceip.os c on  c.uploaduuid =a.uploaduuid "
				+ " group by a.customeruuid ";
		
		entity = new HttpEntity(param, headers);
		ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
		System.out.println(data.getBody());
	}
	
}
