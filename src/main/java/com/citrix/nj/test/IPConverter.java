package com.citrix.nj.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.output.ByteArrayOutputStream;


public class IPConverter {

	public static void main(String[] args) throws IOException {

	  LineIterator li = FileUtils.lineIterator(new File("c:/dbip-country-2016-11.csv"));
	  
//	   File file = new File("dd");
//		byte[] buffer = new byte[1024];
//		//get the zip file content
//	  	ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
//	  	ByteArrayOutputStream bos = new ByteArrayOutputStream();
//	  	//get the zipped file list entry
//	  	ZipEntry ze = zis.getNextEntry();
//	  	while(ze!=null){   	             
//	          int len;
//	          while ((len = zis.read(buffer)) > 0) {
//	          	bos.write(buffer, 0, len);
//	          }           
//	          ze = zis.getNextEntry();
//	  	}
//	  	bos.close();
//	      zis.closeEntry();
//	  	zis.close();
  	
  	
		
  	
	  List<String> output = new ArrayList<String>();
	  while(li.hasNext()){
		  StringBuffer sb = new StringBuffer();
		  String line = li.nextLine();
		  String[] lines = line.split(",");
		  String start = ip2int(lines[0].replaceAll("\"", ""));
		  String end = ip2int(lines[1].replaceAll("\"", ""));	
		  String country = lines[2].replaceAll("\"", "");
		  String outline = start + ":" + end + ":" + country;
		  output.add(outline);
	   }
	  FileUtils.writeLines(new File("c:/ipcountry.txt"), output);
	} 

	public static String ip2int(String ip){
		StringBuffer sb = new StringBuffer();
		String[] ips = ip.split("\\.");
		for(String s: ips){
			sb.append(org.h2.util.StringUtils.pad(s, 3, "0", false));
		}
		return sb.toString();
//		BigInteger big = new BigInteger(sb.toString());
//		System.out.println(big);
//		return big;
	}

}
