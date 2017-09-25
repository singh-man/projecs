package com.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Parsertest {

	private Map<String, List<CxcDesc>> fatCxcmap = new HashMap<String, List<CxcDesc>>();
	

	
	public static void main(String[] args) throws FileNotFoundException, SAXException, IOException, ParserConfigurationException {
		
		SAXParser theParser = SAXParserFactory.newInstance().newSAXParser();
		Parsertest pa = new Parsertest();
		theParser.parse(new FileInputStream("testLicenseMapping.xml"), pa.new MviHandler());

	}

	class MviHandler extends DefaultHandler {
		List<CxcDesc> cxcDescList = new ArrayList<Parsertest.CxcDesc>();
		CxcDesc cxcDesc;
		List<CxcDesc> cxcList;
		boolean isCxcNumber, isPassword, isDescription;
		String fat;
		
		public void characters( char aCharString[], int aStart, int aLength ) {
			
			String s = new String(aCharString, aStart, aLength);
			
			if(isCxcNumber){
				cxcDesc.setCxNumber(s);  isCxcNumber = false;
				//System.out.println(s);
			}
			if(isPassword){
				cxcDesc.setPassword(s);  isPassword = false;
				//System.out.println(s);
			}
			if(isDescription){
				cxcDesc.setDescription(s);  isDescription = false;
				//System.out.println(s);
			}
			/*if("fatNumber".equals(aQName)){
				
			}*/
			
		}
		
		public void startElement( String anUri, String aLocalName,
                String aQName, Attributes anAttributes ) {
			
			if("CxcDesc".equals(aQName)) {
				cxcDesc = new CxcDesc();
			}
			if("password".equals(aQName)) {
				isPassword = true;
			}
			if("cxcNumber".equals(aQName)) {
				isCxcNumber = true;
			}
			if("description".equals(aQName)) {
				isDescription = true;
			}
		}
		
		public void endElement( String anUri, String aLocalName, String aQName ){
			if("CxcDesc".equals(aQName)){
				cxcDescList.add(cxcDesc);
			}
			if("licenseMapping".equals(aQName)) {
				for(CxcDesc cxc : cxcDescList) {
					System.out.println(cxc.cxNumber +": " + cxc.password);
				}
			}
			if("cxcNumberList".equals(aQName)){
				fatCxcmap.put(fat, cxcList);
			}
		}
	}
	
	class CxcDesc {
		String cxNumber, password, description;

		public String getCxNumber() {
			return cxNumber;
		}

		public void setCxNumber(String cxNumber) {
			this.cxNumber = cxNumber;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
}
