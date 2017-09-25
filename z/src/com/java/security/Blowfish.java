package com.java.security;
/** 
 * ********************************************************************* 
 * <p/> 
 * Copyright (c) 1999-2007 Ericsson AB, Sweden. 
 * All rights reserved. 
 * The Copyright to the computer program(s) herein is the property of 
 * Ericsson AB, Sweden. 
 * The program(s) may be used and/or copied with the written permission 
 * from Ericsson AB or in accordance with the terms and conditions  
 * stipulated in the agreement/contract under which the program(s)  
 * have been supplied. 
 * <p/> 
 * ******************************************************************** 
 */ 
 
 
/* -- $Id: Blowfish.java,v 1.4 2001/06/28 10:45:43 nikhil Exp $ -- 
 
 ============================================================================ 
        GNU LESSER GENERAL PUBLIC LICENSE Version 2.1, February 1999  
 ============================================================================ 
 
 This library is free software; you can redistribute it and/or 
 modify it under the terms of the GNU Lesser General Public 
 License as published by the Free Software Foundation; either 
 version 2.1 of the License, or (at your option) any later version. 
 
 This library is distributed in the hope that it will be useful, 
 but WITHOUT ANY WARRANTY; without even the implied warranty of 
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 Lesser General Public License for more details. 
 
 You should have received a copy of the GNU Lesser General Public 
 License along with this library; if not, write to the Free Software 
 Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA 
 
 -- 
 Copyright (C) 2001 Infinity Design <http://www.infinity-design.com> 
 Author: Nikhil Gupte <ngupte@aurigalogic.com> 
*/ 

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


 
/** 
 * Provides Blowfish encryption and decryption. 
 * This class uses Sun Microsystem's JCE 1.2.1 which is available from 
 * <a href="http://java.sun.com/">http://java.sun.com/</a>.  
 * <p> 
 * <b>Example:</b>  
 * <br /> 
 * <code> 
 * &nbsp;&nbsp;String encrypted = Blowfish.encrypt("encryptme"); 
 *<br /> 
 * &nbsp;&nbsp;String decrypted = Blowfish.encrypt(encrypted); 
 * </code> 
 * </p> 
 * <p> 
 * <b>Command Line Example:</b>  
 * <br /> 
 * <code> 
 * java -cp FMMDbPassword.jar com.ericsson.mediation.util.crypto.Blowfish encryptme <br /> 
 * encrypt("encryptme") = 9d9bb242d3ad9502dcd8bfbafbea9e12<br /> 
 * decrypt("9d9bb242d3ad9502dcd8bfbafbea9e12") = encryptme<br /> 
 * </code> 
 * </p> 
 *   
 */ 

public class Blowfish  
{ 
	private final static String[] stringRelationOne = {"4C","57","4F","50","4C","4B","42","43","4F","44","50","4A","4D","4F","3F"}; 
    private final static String[] stringRelationTwo = {"1","2","3","4","5","6","7","7","6","5","4","3","2","1","9"}; 
   
    /** 
     * Encrypts a string using the passed key. 
     *  
     * @param cleartext The clear text string to encrypt. 
     * @param key The key to use for the encryption. 
     * 
     * @return a string representing the resulting ciphertext. 
     */ 
 
    public static String encrypt(String acleartext) throws Exception { 
        return bytesToHex(crypt(acleartext.getBytes(), Cipher.ENCRYPT_MODE).toByteArray()); 
    } 
        
    public static byte[] encryptByteArray(byte[] in)throws Exception {
		return crypt(in, Cipher.ENCRYPT_MODE).toByteArray();
    }
    
    public static String encryptNoPad(String acleartext)throws Exception {
		return bytesToHex(cryptNoPad(acleartext.getBytes(), Cipher.ENCRYPT_MODE).toByteArray());
    }
    
    /** 
      * Decrypts a string using the passed key. 
     *  
     * @param ciphertext The encrypted string. 
     * @param key The key to use for the decryption. 
     * 
     * @return a string representing the resulting cleartext. 
     */ 
    public static String decrypt(String aciphertext) throws Exception { 
        return crypt(hexToBytes(aciphertext), Cipher.DECRYPT_MODE).toString(); 
    } 
    
    public static byte[] decryptStream(InputStream in) throws Exception {
    	return cryptStream(in, Cipher.DECRYPT_MODE).toByteArray();
    }

    public static String decryptNoPad(String aciphertext) throws Exception { 
        return cryptNoPad(hexToBytes(aciphertext), Cipher.DECRYPT_MODE).toString(); 
    }
    
    public static void main(String[] args) throws Exception  
    { 
    	encryptFile("C:\\zzzz\\LicenseMapping.xml",
    			"C:\\dev\\apache-tomcat-6.0.18\\conf\\LicenseMap_CXCFAKE.xml.enc");
        String text = args[0];
        try { 
            String mode = "encrypt"; 
            String encrypted = encrypt(text);   
            System.out.println(mode + "(\"" + text + "\") = " + encrypted); 
 
            mode = "decrypt"; 
            String decrypted = decrypt(encrypted);  
            System.out.println(mode + "(\"" + encrypted + "\") = " + decrypted); 
 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    }

    /* 
     * This actually does the encryption/decryption. 
     */ 
    private static ByteArrayOutputStream crypt(byte[] aInput, int aMode) throws Exception {
    	return cryptStream(new ByteArrayInputStream(aInput), aMode);
    }
    
    private static ByteArrayOutputStream cryptStream(InputStream in, int aMode) throws Exception {
    	// Install SunJCE provider 
        Provider sunJce = new com.sun.crypto.provider.SunJCE(); 
        Security.addProvider(sunJce); 

        KeyGenerator kgen = KeyGenerator.getInstance("Blowfish"); 
        kgen.init(448); 
        SecretKey skey = kgen.generateKey(); 
        byte[] raw = hexStringmask(stringRelationOne, stringRelationTwo).getBytes(); 
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish"); 
        Cipher cipher = Cipher.getInstance("Blowfish/ECB/PKCS5Padding"); 
        cipher.init(aMode, skeySpec); 
         
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
        CipherOutputStream cos = new CipherOutputStream(bos, cipher); 
         
        int length = 0; 
        byte[] buffer = new byte[8192]; 
        while ((length = in.read(buffer)) != -1) { 
            cos.write(buffer, 0, length); 
        } 
         
        in.close(); 
        cos.close(); 
        return bos; 
    }
 
    private static ByteArrayOutputStream cryptNoPad(byte[] aInput, int aMode) throws Exception { 
    	// Install SunJCE provider 
    	Provider sunJce = new com.sun.crypto.provider.SunJCE(); 
    	Security.addProvider(sunJce); 

    	KeyGenerator kgen = KeyGenerator.getInstance("Blowfish"); 
    	kgen.init(448); 
    	SecretKey skey = kgen.generateKey(); 
    	byte[] raw = hexStringmask(stringRelationOne, stringRelationTwo).getBytes(); 
    	SecretKeySpec skeySpec = new SecretKeySpec(raw, "Blowfish"); 
    	Cipher cipher = Cipher.getInstance("Blowfish/ECB/NoPadding"); 
    	cipher.init(aMode, skeySpec); 

    	if(aMode == Cipher.DECRYPT_MODE) {
    		byte[] decrypted = null;
    		decrypted = cipher.doFinal(aInput);
    		int leng = 0;
    		int a = decrypted.length;

    		while (a != 0 &&  leng < a && decrypted[leng] != 0x0 ) {
    			leng++;
    		}
    		byte[] Final = new byte[leng];
    		int j = 0;
    		while (j< leng && decrypted[j] != 0x0) {
    			Final[j] = decrypted[j];
    			j++;
    		}
    		ByteArrayOutputStream bosDecrypted = new ByteArrayOutputStream();
    		bosDecrypted.write(Final,0,leng);
    		return bosDecrypted;
    	}

    	int Limit = 8 - (aInput.length % 8);
    	byte[] paddedInput = new byte[aInput.length + Limit];
    	for (int i=0;i<aInput.length;i++){
    		paddedInput[i] = aInput[i];
    	}

    	for(int i = aInput.length ;i < aInput.length+Limit;i++ ) {
    		paddedInput[i] = 0x0;
    	}

    	ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
    	ByteArrayInputStream bis = new ByteArrayInputStream(paddedInput);      
    	CipherOutputStream cos = new CipherOutputStream(bos, cipher); 

    	int length = 0; 
    	byte[] buffer = new byte[8192];
    	while ((length = bis.read(buffer)) != -1) { 
    		cos.write(buffer, 0, length);
    	} 
    	bis.close(); 
    	cos.close(); 
    	return bos; 
    } 
        
    private static String bytesToHex(byte[] b) {     
        int size = b.length; 
        StringBuffer h = new StringBuffer(size); 
        for (int i = 0; i < size; i++) { 
            int u = b[i]&255; // unsigned conversion 
            if (u<16) { 
                h.append("0"+Integer.toHexString(u)); 
            } else { 
                h.append(Integer.toHexString(u)); 
            } 
        } 
        return h.toString(); 
    } 
 
    private static byte[] hexToBytes(String str) { 
        if (str == null) 
            return new byte[0] ; 
 
        int len = str.length();    // probably should check length 
        char hex[] = str.toCharArray(); 
        byte[] buf = new byte[len/2]; 
 
        for (int pos = 0; pos < len / 2; pos++) 
            buf[pos] = (byte)( ((toDataNibble(hex[2*pos]) << 4) & 0xF0) 
                            | ( toDataNibble(hex[2*pos + 1])   & 0x0F) ); 
 
        return buf; 
    }    
 
    private static byte toDataNibble(char c) { 
        if (('0' <= c) && (c <= '9' )) 
            return (byte)((byte)c - (byte)'0'); 
        else if (('a' <= c) && (c <= 'f' )) 
            return (byte)((byte)c - (byte)'a' + 10); 
        else if (('A' <= c) && (c <= 'F' )) 
            return (byte)((byte)c - (byte)'A' + 10); 
        else 
            return -1; 
    } 
     
    public static void encryptFile(String aInputFileName, String aOutputFileName) throws Exception { 
        File theFile = new File(aInputFileName); 
        if(theFile.exists()) { 
            String text = readFileAsString(aInputFileName);
            if(text == null) {
                throw new Exception("Unable to encrypt file. Input string null");
            }
            String encryptedString = Blowfish.encrypt(text); 
            writeStringToFile(encryptedString, aOutputFileName); 
        } 
    } 
     
    public static String decryptFile(String aFileName) throws Exception { 
            String text = readFileAsString(aFileName);
            if(text == null || text.trim().equals("")) {
                throw new Exception("Unable to decrypt file. Encrypted string null");
            }
            String decryptedString = Blowfish.decrypt(text); 
            return decryptedString; 
    } 
     
    public static String hexStringmask(String[] s, String[] s1) {    	
    	byte b[] = new byte[s.length];
    	byte b1[]= new byte[s.length];
    	int num,num1;
        for(int i=0;i<s.length;i++) {
            num=Integer.parseInt(s[i], 16);
            num1=Integer.parseInt(s1[i], 16);
        	b[i] = Byte.valueOf(Integer.toString(num));
        	b1[i] = Byte.valueOf(Integer.toString(num1));
        	b[i] = (byte)(b[i]^ b1[i]);
        }
        String str= new String(b);
        return str;
    } 

    
    /* Do not use this function to read the file into string, if there is any possibility of string length 
     * being more than Integer.MAX_VALUE (in excess of two billion) characters.
     * (File size should be less than 2GB approx)
     */
    private static String readFileAsString( String afilePath) throws Exception { 
        String fileString = null;
        try {
            StringBuffer fileData = new StringBuffer(1000); 
            BufferedReader reader = new BufferedReader(new FileReader(afilePath)); 
            char[] buf = new char[1024]; 
            int numRead=0; 
            
            while((numRead=reader.read(buf)) != -1) { 
                String readData = String.valueOf(buf, 0, numRead); 
                fileData.append(readData); 
                buf = new char[1024]; 
            } 
            reader.close();
            fileString = fileData.toString();
        }
        catch(OutOfMemoryError e) {
            throw new Exception("Unable to read file. File too big");
        }
        return fileString;
    } 
     
    private static void writeStringToFile(String adecrptedString, String afilename) throws Exception { 
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(afilename)); 
            out.write(adecrptedString); 
            out.close(); 
        }  
        catch (IOException e) {  
            throw new Exception("Exception occurred while writing string to file"); 
        } 
    } 
    
}
