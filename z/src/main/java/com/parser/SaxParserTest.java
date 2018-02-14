package com.parser;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * Idea: 
 * 		Fat1:{{CXC1,pwd1},{CXC2,pwd2},{CXC3,pwd3}}
 * 		Fat2:{{CXC2,pwd2},{CXC4,pwd4},{CXC5,pwd5}}
 * 			  {CXC2,pwd2} this unit is considered an Object of LicenseInfo
 * 
 * Input: xml data as provided as follows
 * Output: Map<String, List<LicenseInfo>>
 * 
 * <license>	
 *	<Fat id="FAT1">
 *		<licenseInfo>
 *			<CXC>CXC1</CXC>
 *			<pwd>pwd1</pwd>
 *		</licenseInfo>
 *		<licenseInfo>
 *			<CXC>CXC2</CXC>
 *			<pwd>pwd2</pwd>
 *		</licenseInfo>
 *		<licenseInfo>
 *			<CXC>CXC3</CXC>
 *			<pwd>pwd3</pwd>
 *		</licenseInfo>
 *	</Fat>
 *	<Fat id="FAT2">
 *		<licenseInfo>
 *			<CXC>CXC1</CXC>
 *			<pwd>pwd1</pwd>
 *		</licenseInfo>
 *		<licenseInfo>
 *			<CXC>CXC4</CXC>
 *			<pwd>pwd4</pwd>
 *		</licenseInfo>
 *	</Fat>
 *	<Fat id="FAT3">
 *		<licenseInfo>
 *			<CXC>CXC3</CXC>
 *			<pwd>pwd3</pwd>
 *		</licenseInfo>
 *		<licenseInfo>
 *			<CXC>CXC5</CXC>
 *			<pwd>pwd5</pwd>
 *		</licenseInfo>
 *	</Fat>
 *	<Fat id="FAT4">
 *		<licenseInfo>
 *			<CXC>CXC1</CXC>
 *			<pwd>pwd1</pwd>
 *		</licenseInfo>
 *		<licenseInfo>
 *			<CXC>CXC2</CXC>
 *			<pwd>pwd2</pwd>
 *		</licenseInfo>
 *		<licenseInfo>
 *			<CXC>CXC5</CXC>
 *			<pwd>pwd5</pwd>
 *		</licenseInfo>
 *	</Fat>
 * </license>
 */

public class SaxParserTest {

	Map<String, List<LicenseInfo>> fatCxcMap = new HashMap<String, List<LicenseInfo>>();

	private void handleXML(XMLReader xmlReader, InputSource is) throws IOException, SAXException{
		xmlReader.setContentHandler(new MyHandler());
		xmlReader.parse(is);
	}

	public static void main (String args[]) {

		FileInputStream instream = null;
		InputSource is=null;
		try {
			instream = new FileInputStream("C:\\toBeDeleted\\test1.xml");
			is = new InputSource(instream);

			SAXParserFactory spf = SAXParserFactory.newInstance();
			spf.setNamespaceAware(true);
			XMLReader xmlReader = spf.newSAXParser().getXMLReader();

			SaxParserTest saxParserTest = new SaxParserTest();
			saxParserTest.handleXML(xmlReader, is);
			System.out.println(" -- listing ");
			Set<String> fatSet = saxParserTest.fatCxcMap.keySet();
			for(String fat : fatSet) {
				List<LicenseInfo> licenseInfos = saxParserTest.fatCxcMap.get(fat);
				for(LicenseInfo l : licenseInfos) {
					System.out.println(fat + ": " + l.getCxc() + ": " + l.getPwd()); 
				}
			}

		} catch( Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

	}


	private class LicenseInfo {

		private String cxc;
		private String pwd;

		public void setCxc(String cxc) {
			this.cxc = cxc;
		}

		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

		public LicenseInfo(String cxc, String pwd) {
			this.cxc = cxc;
			this.pwd = pwd;
		}

		public String getCxc() {
			return cxc;
		}

		public String getPwd() {
			return pwd;
		}
	}

	class MyHandler extends DefaultHandler {

		String fat, tempVal;
		LicenseInfo tempLicenseInfo;
		List<LicenseInfo> licenseInfoList;

		public void characters(char[] ch, int start, int length) {
			String s = new String(ch,start,length);
			tempVal = s.trim();
		}

		public void startElement(String namespaceURI, String localName, String qName, Attributes attributes) {
			if(localName.equals("Fat")) {
				fat = attributes.getValue(namespaceURI,"id");
				licenseInfoList = new ArrayList<SaxParserTest.LicenseInfo>();
			}
			else if(localName.equals("licenseInfo")) {
				tempLicenseInfo = new LicenseInfo(null, null);
			}
		}

		public void endElement(String namespaceURI,String localName,String qName){
			if(localName.equals("licenseInfo"))
				licenseInfoList.add(tempLicenseInfo);
			else if(localName.equals("CXC"))
				tempLicenseInfo.setCxc(tempVal);
			else if(localName.equals("pwd"))
				tempLicenseInfo.setPwd(tempVal);
			else if(localName.equals("Fat"))
				fatCxcMap.put(fat, licenseInfoList);
		}
	}
}