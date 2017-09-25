package com.parser;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class MviXmlParser2 {

	private Map<String, List<String>> fatCxcMap = new HashMap<String, List<String>>();
	private Map<String, LicenseInfo> cxcMap = new HashMap<String, MviXmlParser2.LicenseInfo>();

	public Map<String, List<String>> getFatCxcMap() {
		return fatCxcMap;
	}

	public Map<String, LicenseInfo> getCxcMap() {
		return cxcMap;
	}
	
	public String[] getFatArray() {
		Set<String> fatSet = fatCxcMap.keySet();
		return fatSet.toArray(new String[fatSet.size()]);
	}

	private <T> void printMap(Map<String, T> aMap) {
		Set<String> mapSet = aMap.keySet();
		for(String s : mapSet) {
			if(aMap.get(s) instanceof List)
				System.out.println(s + ": " + aMap.get(s));
			else {
				String pwd = ((LicenseInfo)aMap.get(s)).getPwd();
				String desc = ((LicenseInfo)aMap.get(s)).getDesc();
				System.out.println(s + ": " + desc + ": " + pwd);
			}
		}
	}
	
	public void init() {
		FileInputStream instream = null;
		InputSource is=null;
		try {
			instream = new FileInputStream("C:\\toBeDeleted\\testLicenseMapping.xml");
			is = new InputSource(instream);

			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setNamespaceAware(true);
			XMLReader xmlReader = spf.newSAXParser().getXMLReader();

			xmlReader.setContentHandler(new MyHandler());
			xmlReader.parse(is);

			printMap(fatCxcMap);
			printMap(cxcMap);

		} catch( Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public MviXmlParser2(){
		init();
	}
	
	public static void main (String args[]) {
		new MviXmlParser2();
	}

	class MyHandler extends DefaultHandler {

		//XML tag listing
		private final String FAT_NUMBER = "fatNumber";
		private final String CXC_DESC = "CxcDesc";
		private final String CXC_NUMBER = "CXCNumber";
		private final String CXC_ID = "cxcId";
		private final String PASSWORD = "password";
		private final String DESCRIPTION = "description";

		private String tempValue;
		private List<String> cxcList;
		LicenseInfo licenseInfo;

		public void characters(char[] ch, int start, int length) {
			String s = new String(ch,start,length).trim();
			if(!s.equals("")) 
				tempValue = s;
		}

		public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) {
			if(FAT_NUMBER.equals(localName)) {
				cxcList = new ArrayList<String>();
			}
			else if(CXC_DESC.equals(localName)) {
				licenseInfo = new LicenseInfo();
			}
		}

		public void endElement(String namespaceURI,String localName,String qName){
			if(tempValue != null) {
				if(FAT_NUMBER.equals(localName)) {
					fatCxcMap.put(tempValue, cxcList);
				}
				else if(CXC_NUMBER.equals(localName)) {
					cxcList.add(tempValue);
				}
				else if(CXC_ID.equals(localName)) {
					cxcMap.put(tempValue,licenseInfo);
				}
				else if(PASSWORD.equals(localName)){
					licenseInfo.setPwd(tempValue);
				}
				else if(DESCRIPTION.equals(localName)){
					licenseInfo.setDesc(tempValue);
				}
				tempValue = null;
			}
		}
	}

	private class LicenseInfo {

		private String desc;
		private String pwd;

		public String getPwd() {
			return pwd;
		}
		public void setPwd(String pwd) {
			this.pwd = pwd;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
	}
}
