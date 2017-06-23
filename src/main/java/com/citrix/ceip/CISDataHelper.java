package com.citrix.ceip;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Configuration
public class CISDataHelper {
	protected static Logger log = LoggerFactory.getLogger(CISDataHelper.class);
	private static long TOKEN_MAX_LAST_TIME = 600L*1000;
	private String baseUrl = "https://cis.citrix.com";
	private String cisUser = "";
	private String cisPwd = "";
	private String accessToken = "";
	private long lastAccessTime = 0;
	private RestTemplate restTemplate = new RestTemplate();
	private ObjectMapper mapper = new ObjectMapper();
	
	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getCisUser() {
		return cisUser;
	}

	public void setCisUser(String cisUser) {
		this.cisUser = cisUser;
	}

	public String getCisPwd() {
		return cisPwd;
	}

	public void setCisPwd(String cisPwd) {
		this.cisPwd = cisPwd;
	}

	private HttpEntity generateHeaders() {
		getAccessToken();
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set(HttpHeaders.AUTHORIZATION, "BT " + accessToken);
		HttpEntity entity = new HttpEntity(headers);
		return entity;
	}
	
	public ArrayNode getSqlResult(String sql ) throws IOException{	
		getAccessToken();
		
		String url = baseUrl + "/dw/api/sql?q=" + sql;
		ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.GET, generateHeaders(), String.class);
	    JsonNode node = mapper.readTree(data.getBody());
	    ArrayNode resultNode = (ArrayNode)node.get("result");				
	    return resultNode;
	}
	
	
	public ArrayNode getAzureSqlResult(String sql ) throws IOException{	
		getAccessToken();
		
		String url = baseUrl + "/dw/api/sql-azure/";
		Map<String,Object> param = new HashMap<String,Object>();
		param.clear();
		param.put("timeout", 360000);
		param.put("sql", sql);
		
		HttpEntity entity = new HttpEntity(param, generateHeaders().getHeaders());
		
		ResponseEntity<String> data = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	    JsonNode node = mapper.readTree(data.getBody());
	    ArrayNode resultNode = (ArrayNode)node.get("data");				
	    return resultNode;
	}
	
	private String getAccessToken(){
		synchronized (accessToken) {
			if((System.currentTimeMillis() - lastAccessTime) <= TOKEN_MAX_LAST_TIME ){
				return accessToken;
			}
		}		
		
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("username", cisUser);
		param.put("password", cisPwd);
		
		HttpEntity entity = new HttpEntity(param, headers);
		
		//1. identity token
		String url = baseUrl + "/auth/api/create_identity/";
		ResponseEntity<Token> response = restTemplate.exchange(url, HttpMethod.POST, entity, Token.class);	
		String idToken = response.getBody().getToken();
		log.info("identity token: " + idToken);
		
		//2. grant token:
		headers.add(HttpHeaders.AUTHORIZATION, "BT " + idToken);
		url = baseUrl + "/dw/api/create_grant/";
		param.clear();
		param.put("max_age", 7200);
		entity = new HttpEntity(param, headers);
		response = restTemplate.exchange(url, HttpMethod.POST, entity, Token.class);	
		String grantToken = response.getBody().getToken();
		log.info("grant token: " + grantToken);
		
		//3.Retrieve data access token
		headers.set(HttpHeaders.AUTHORIZATION, "BT " + grantToken);
		url = baseUrl + "/dw/api/grant_access/";
		param.clear();
		param.put("max_age", 7200);
		entity = new HttpEntity(param, headers);
		response = restTemplate.exchange(url, HttpMethod.POST, entity, Token.class);	
		accessToken = response.getBody().getToken();
		
		lastAccessTime = System.currentTimeMillis();
		log.info("access token @timestamp=" + lastAccessTime + " token: " + accessToken);	
		
		return accessToken;
	}

	public static class Token {
		
		private String token;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}		
	}
}
