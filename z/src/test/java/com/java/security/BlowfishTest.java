package com.java.security;

import com.java.security.Blowfish;
import static org.junit.Assert.*;

import org.junit.Test;


public class BlowfishTest {

	private String thule = "Ericsson1";
	private String encThule = "8a560a2d8a76fb1b";
	
	@Test
	public void testEncrypt() throws Exception {
		String encString = Blowfish.encrypt(thule);
		System.out.println(encString);
		assertEquals(encThule, encString);
	}

	@Test
	public void testEncryptByteArray() {
		fail("Not yet implemented");
	}

	@Test
	public void testEncryptNoPad() {
		fail("Not yet implemented");
	}

	@Test
	public void testDecrypt() throws Exception {
		String decString = Blowfish.decrypt(encThule);
		System.out.println(decString);
		assertEquals(thule, decString);
	}

	@Test
	public void testDecryptStream() {
		fail("Not yet implemented");
	}

	@Test
	public void testDecryptNoPad() {
		fail("Not yet implemented");
	}

	@Test
	public void testEncryptFile() throws Exception {
		Blowfish.encryptFile("/home/emmhssh/.gvfs/sftp for emmhssh on ingrx034/workarea/FE/emmhssh/FE_Source_clone_2/FE_Source/mediationManager/resources/LicenseMapping.xml",
    			"/home/emmhssh/dev/apache-tomcat-6.0.18/conf/LicenseMap_CXCFAKE.xml.enc");
       
	}

	@Test(expected=Exception.class)
	public void testDecryptFile() throws Exception {
		String decryptedString = Blowfish.decryptFile("/home/emmhssh/LicenseMap_CXC1734004_P1A_4.xml.enc");
		System.out.println(decryptedString);
	}

	@Test
	public void testHexStringmask() {
		fail("Not yet implemented");
	}

}
