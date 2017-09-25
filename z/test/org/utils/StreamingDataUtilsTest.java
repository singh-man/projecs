package org.utils;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.Deflater;
import java.util.zip.GZIPOutputStream;

import org.junit.Before;
import org.junit.Test;

public class StreamingDataUtilsTest {

	private FileInputStream fis;
	private InputStream is;
	private Date da;
	private String testString;
	private String testResourcePath;

	@Before
	public void setUp() throws Exception {
		testResourcePath = "/home/emmhssh/man/zzzz/";
		try {
			fis = new FileInputStream(new File(testResourcePath + "/mysql.sql"));
		} catch (FileNotFoundException e) {
		}
		da = new Date();
		testString = "This is the test string used everywhere and also used to " +
				"check the compression utility too. Hope its work accordigly " +
				"and in proper way with the proper compression that shows the " +
				"compression of passed object.";
	}

	@Test
	public void testSizeOfInpStream() {
		StreamingDataUtils.sizeOfInpStream("Size of input stream and stream is destroyed", fis);
	}

	@Test
	public void testSizeOfInputStream() {
		is = StreamingDataUtils.sizeOfInputStream("Size of input stream not destroyed", fis);
		StreamingDataUtils.sizeOfInpStream("Size of input stream previously not destroyed", is);
		StreamingDataUtils.sizeOfInpStream("will give 0 as stream is destroyed", is);
	}

	@Test
	public void testSizeOfOutStream() {
		fail("Not yet implemented");
	}

	@Test
	public void testSerializeDeserializeTestClassObjectToAndFromFile() {
		String dateFormat = getDateInFormat();
		TestClassForSerialization t = new TestClassForSerialization();
		t.setInsI(100); t.setInsJ(200); t.setInsS("manish"); 
		TestClassForSerialization.setStaticK(1000); TestClassForSerialization.setStaticL(2000); TestClassForSerialization.setStaticString("singh");
		StreamingDataUtils.serializeObjectToFile(t, testResourcePath + "serialize/", dateFormat);
		TestClassForSerialization t1 = StreamingDataUtils.deSerializeFileToObject(testResourcePath + "serialize/class org.utils.TestClassForSerialization-" + dateFormat);
		System.out.println("Deserilize object is : " + t1);
		
		// Surprisingly static variables can not be serialized 
		// here it is working bcoz JVM is not shut down
		System.out.println(t1.getStaticK() + " : " + TestClassForSerialization.getStaticK()); 
	}
	
	@Test
	public void testDeserializeTestClassObjectFromFile() {
		TestClassForSerialization t1 = StreamingDataUtils.deSerializeFileToObject(testResourcePath + "serialize/class org.utils.TestClassForSerialization-" + "20-04-2012 23-27-42");
		System.out.println("Deserilize object is : " + t1);
		// Static variables are not serialized
		System.out.println(t1.getStaticK() + " : " + TestClassForSerialization.getStaticK()); // Surprisingly static variables can be serialized and once the class is loaded there value is update to the new serialized value
	}

	@Test
	public void testSerializeDeserializeTestClassObjectToAndFromFileViaXmlEncoder() {
		String dateFormat = getDateInFormat();
		TestClassForSerialization t = new TestClassForSerialization();
		t.setInsI(100); t.setInsJ(200); t.setInsS("manish"); 
		TestClassForSerialization.setStaticK(1000); TestClassForSerialization.setStaticL(2000); TestClassForSerialization.setStaticString("singh");
		StreamingDataUtils.xmlSerializationToFile(t, testResourcePath + "serialize/", dateFormat);
		TestClassForSerialization t1 = StreamingDataUtils.xmlDeSerializationFrom(testResourcePath + "serialize/class org.utils.TestClassForSerialization-"+dateFormat);
		System.out.println("Deserilize object is : " + t1);
		System.out.println(t1.getStaticK() + " : " + TestClassForSerialization.getStaticK());
	}

	private String getDateInFormat() {
		SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
		String dateFormat = sf.format(new Date());
		return dateFormat;
	}

	@Test
	public void testSerializeDeserializeObjectToAndFromByteArrayOutputStream() throws IOException {
		//Serialize
		ByteArrayOutputStream baos = StreamingDataUtils.serializeObjectToByteArrayOutputStream(testString);

		ByteArrayOutputStream tmpBaos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(tmpBaos);
		oos.writeObject(testString);
		oos.flush(); oos.close();
		baos.close();

		System.out.println("Expected = " + baos.toByteArray().length + " Actual = " + tmpBaos.toByteArray().length + 
				" Absolute Actual = " + testString.getBytes().length);

		//DeSerialize
		String deserString = (String) StreamingDataUtils.deSerializeByteArrayOutputStreamToObject(baos);
		assertEquals(testString, deserString);

	}

	@Test
	public void testDeserializeByteArrayInputStreamToObject() throws IOException {
		/*
		 * Getting byte array from string and giving it to ByteArrayInputStream 
		 * is not fruitful
		 */
		ByteArrayOutputStream baos = StreamingDataUtils.serializeObjectToByteArrayOutputStream(testString);
		assertEquals(testString, StreamingDataUtils.deSerializeByteArrayInputStreamToObject(new ByteArrayInputStream(baos.toByteArray())));
	}

	@Test
	public void testSerializeDeserializeCompressObjectToAndFromByteArrayOutputStream() throws IOException {
		ByteArrayOutputStream compressBaos = StreamingDataUtils.serializeAndCompressObjectToByteArrayOutputStream(testString);

		System.out.println("Compressed size = " + compressBaos.toByteArray().length + " Actual size = " + testString.getBytes().length);

		if(compressBaos.toByteArray().length >= testString.getBytes().length)
			fail("compressed size should be : " + compressBaos.toByteArray().length + 
					" < than original size : " + testString.getBytes().length);

		String tString = (String) StreamingDataUtils.deSerializeCompressByteArrayInputStreamToObject(new ByteArrayInputStream(compressBaos.toByteArray()));
		assertEquals(testString, tString);
	}

	@Test
	public void testSerializeDeserializeCompressObjectToAndFromFile() {
		String dateFormat = getDateInFormat();
		//Serialize
		StreamingDataUtils.serializeAndCompressObjectToFile(testString, testResourcePath + "serialize/", dateFormat);
		
		//DeSerialize
		String s = StreamingDataUtils.deSerializeCompressedFileToObject(testResourcePath + "serialize/class java.lang.String-compress-" + dateFormat);
		System.out.println("Deserilize -compress- object is : " + s);
		assertEquals(testString, s);
	}

	@Test
	public void testTransferOutputStreamToInputStream() {
		fail("Not yet implemented");
	}

	@Test
	public void testTransferOutputStreamToOutputStream() {
		fail("Not yet implemented");
	}

	@Test
	public void testTransferInputStreamToOutputStream() {
		byte[] by = new byte[]{'a','4','5','h'};
		ByteArrayInputStream bais = new ByteArrayInputStream(by);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		StreamingDataUtils.transferInputStreamToOutputStream(bais, baos);
		assertArrayEquals(by, baos.toByteArray());
	}

	@Test
	public void testSizeOfObject() {
		StreamingDataUtils.sizeOfObject("Size of object passed is", testString);
	}

	@Test
	public void testCompression() throws IOException {
		ByteArrayOutputStream baos = null;
		/*String testString_1 = readStringFromFile("C:\\manish\\zzz\\ab.txt");
		System.out.println("String size " + testString_1.getBytes().length);

		baos = StreamingDataUtils.serializeObjectToByteArrayOutputStream(testString_1);
		System.out.println("normal " + baos.toByteArray().length + " " + baos.size());
		baos = StreamingDataUtils.serializeAndCompressObjectToByteArrayOutputStream(testString_1);
		System.out.println("compressed " + baos.toByteArray().length);

		baos = StreamingDataUtils.
				serializeAndCompressObjectToByteArrayOutputStreamWithCompressionLevel(
						testString_1, Deflater.BEST_COMPRESSION);
		System.out.println("compressed level " + baos.toByteArray().length);*/
		FileInputStream fis = new FileInputStream(new File("C:\\Users\\emmhssh\\Documents\\MM\\Work\\configsSettings\\CSDK\\CSDK.jar"));
		FileInputStream fis1 = new FileInputStream(new File("C:\\Users\\emmhssh\\Documents\\MM\\Work\\configsSettings\\FBC_PROCESS1_20110523_AddedRM02.xml"));
		baos = new ByteArrayOutputStream();

		/*StreamingDataUtils.transferInputStreamToOutputStream(fis1, baos);
		System.out.println("normal " + baos.toByteArray().length);*/

		/*StreamingDataUtils.transferInputStreamToOutputStream(fis1, new GZIPOutputStream(baos));
		System.out.println("compress " + baos.toByteArray().length);*/

		StreamingDataUtils.transferInputStreamToOutputStream(fis1, new GZIPOutputStream(baos){
			{
				def.setLevel(Deflater.BEST_COMPRESSION);
			}
		});
		System.out.println("compress level " + baos.toByteArray().length);
	}

	private String readStringFromFile(String path) {
		String s = null;
		try {
			FileInputStream fis = new FileInputStream(new File(path));

			int len = 0;
			byte[] buff = new byte[1024];
			StringBuffer sb = new StringBuffer();
			while((len = fis.read(buff)) > 0) {
				sb.append(new String(buff));
			}
			s = sb.toString();
			fis.close();
		} catch(Exception e) {

		}
		return s;
	}

}
