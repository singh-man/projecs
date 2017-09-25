package com.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

/**
 * StAX itself has been derived from an approach called XML Pull Parsing.
 * This approach is quite light-weight and particularly suitable for resource-constrained environments, such as J2ME
 *
 * However, few implementations provided enterprise-level features such as validation and thus XML Pull has never caught on among enterprise Java developers
 * @author emmhssh
 *
 */

public class XmlStaxParser {

	private Map<String, List<String>> fatCxcMap = new HashMap<String, List<String>>();
	private Map<String, LicenseInfo> cxcMap    = new HashMap<String, XmlStaxParser.LicenseInfo>();

	//XML tag listing
	private final String FAT_NUMBER = "fatNumber";
	private final String CXC_DESC = "CxcDesc";
	private final String CXC_NUMBER = "CXCNumber";
	private final String CXC_ID = "cxcId";
	private final String PASSWORD = "password";
	private final String DESCRIPTION = "description";

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

	/**
	 * event iterator-based approach offers an important advantage over the cursor-based method. 
	 * By turning parser events into first-class objects, it allows the application to process 
	 * them in an object-oriented fashion.
	 * 
	 * @param eventReader
	 * @throws XMLStreamException
	 */
	private void printViaXMLEventReader(XMLEventReader eventReader) throws XMLStreamException {
		while (eventReader.hasNext()) {
			XMLEvent e = eventReader.peek();

			switch(e.getEventType()) {
			case XMLStreamConstants.START_DOCUMENT:
				System.out.println("Start Document: " + e);
				break;
			case XMLStreamConstants.CHARACTERS:
				if (!e.toString().trim().equals(""))
					System.out.println("Text: " + e);
				break;
			case XMLStreamConstants.END_DOCUMENT:
				System.out.println("End element: " + e);
				break;
			}
			if(e.isStartElement()) {
				StartElement start = e.asStartElement();
				System.out.println("Start Element: " + start.getName());
				Iterator<Attribute> it = start.getAttributes();
				while(it.hasNext()) {
					Attribute at = it.next();
					System.out.println("ATTR: " + at.getName() + " = " + at.getValue());
				}
			}
			if(e.isEndElement())
				System.out.println("End element: " + e);
			
			eventReader.nextEvent();
		}
	}

	/**
	 * Cursor Based API XMLStreamReader
	 *
	 * As you can see, cursor-based API is all about efficiency. All state information is
	 * available directly from the stream reader and no extra objects are created. This is 
	 * especially useful in applications where performance and low memory footprint 
	 * are highly important.
	 * 
	 * @param streamReader
	 * @throws XMLStreamException
	 */
	private void printViaStreamReader(XMLStreamReader streamReader) throws XMLStreamException {
		try {
			int event = streamReader.getEventType();
			while (true) {
				switch (event) {
				case XMLStreamConstants.START_DOCUMENT:
					System.out.println("Start Document.");
					break;
				case XMLStreamConstants.START_ELEMENT:
					System.out.println("Start Element: " + streamReader.getName());
					for(int i = 0, n = streamReader.getAttributeCount(); i < n; ++i)
						System.out.println("Attribute: " + streamReader.getAttributeName(i) + "=" + streamReader.getAttributeValue(i));
					break;
				case XMLStreamConstants.CHARACTERS:
					if (streamReader.isWhiteSpace())
						break;
					System.out.println("Text: " + streamReader.getText());
					break;
				case XMLStreamConstants.END_ELEMENT:
					System.out.println("End Element:" + streamReader.getName());
					break;
				case XMLStreamConstants.END_DOCUMENT:
					System.out.println("End Document.");
					break;
				}

				if (!streamReader.hasNext())
					break;

				event = streamReader.next();
			}
		} finally {
			streamReader.close();
		}
	}

	public void init() throws FileNotFoundException, XMLStreamException{
		XMLInputFactory factory = XMLInputFactory.newInstance();
		factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);

		String xml = this.getClass().getResource("testLicenseMapping.xml").getFile();

		XMLEventReader eventReader = factory.createXMLEventReader(new FileReader(xml));
		XMLStreamReader streamReader = factory.createXMLStreamReader(new FileReader(xml));

		//printViaStreamReader(streamReader);

		printViaXMLEventReader(eventReader);

	}

	public XmlStaxParser(){
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args){
		new XmlStaxParser();
	}

	class LicenseInfo {
		String password;
		String description;

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