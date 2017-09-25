package com.java.security;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class InstallCert2 {

	public static void main(String[] args) throws Exception {
		KeyStore ks = loadExistingCacerts();
		Certificate cert = loadTheNewCertificate();
		exportNewCacerts(ks, cert);
	}

	private static void exportNewCacerts(KeyStore ks, Certificate certs)
			throws KeyStoreException, IOException, NoSuchAlgorithmException,
			CertificateException, FileNotFoundException {
		KeyStore.Entry newEntry = new KeyStore.TrustedCertificateEntry(certs);
		ks.setEntry("ericssonCertificate", newEntry, null);
		ks.store(new FileOutputStream(new File("/home/emmhssh/jssecacerts")), "changeit".toCharArray());
	}

	private static Certificate loadTheNewCertificate()
			throws CertificateException, IOException {
		String newCertPath = "/home/emmhssh/dev/src/mm/onm/common_ui/src/main/resources/clientproperties/mediationCerts.pem";
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream certstream = new FileInputStream(newCertPath);
		Certificate certs =  cf.generateCertificate(certstream);
		return certs;
	}

	private static KeyStore loadExistingCacerts()
			throws FileNotFoundException, KeyStoreException, IOException,
			NoSuchAlgorithmException, CertificateException {
		File file = locateTheExistingCertFileInJVM();
		System.out.println("Loading KeyStore " + file + "...");
		InputStream in = new FileInputStream(file);
		KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks.load(in, "changeit".toCharArray());
		in.close();
		return ks;
	}

	private static File locateTheExistingCertFileInJVM() {
		File file = new File("jssecacerts");
		if (file.isFile() == false) {
			char SEP = File.separatorChar;
			File dir = new File(System.getProperty("java.home") + SEP + "lib" + SEP + "security");
			file = new File(dir, "jssecacerts");
			if (file.isFile() == false) {
				file = new File(dir, "cacerts");
			}
		}
		return file;
	}
}