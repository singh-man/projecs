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

/**
 * If ever encountered with any problem while opening or adding the new
 * certificate. Try to make/convert the certificates to x.509 or PEM format
 *
 * @author emmhssh
 *
 */
public class CertificateHandlerUtility {

    public void exportNewCacerts(KeyStore ks, String filePath, String password)
            throws KeyStoreException, IOException, NoSuchAlgorithmException,
            CertificateException, FileNotFoundException {
        ks.store(new FileOutputStream(new File(filePath)), password.toCharArray());
    }

    public CertificateHandlerUtility appendToCacerts(KeyStore ks, Certificate certs)
            throws KeyStoreException {
        KeyStore.Entry newEntry = new KeyStore.TrustedCertificateEntry(certs);
        ks.setEntry("ericssonCertificate", newEntry, null);
        return this;
    }

    public Certificate loadNewCertificate(String newCertificatePath)
            throws CertificateException, IOException {
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        InputStream certstream = new FileInputStream(newCertificatePath);
        Certificate certs = cf.generateCertificate(certstream);
        return certs;
    }

    public KeyStore loadCacertsFromPath(File file)
            throws FileNotFoundException, KeyStoreException, IOException,
            NoSuchAlgorithmException, CertificateException {
        System.out.println("Loading KeyStore " + file + "...");
        InputStream in = new FileInputStream(file);
        KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
        ks.load(in, "changeit".toCharArray());
        in.close();
        return ks;
    }

    public File locateJVMCacerts() {
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

    /**
     * Certificate need to be in x.509 format.
     */
    public void addNewCertToExistingCert(File f, String pwd, String... newCertPath) throws FileNotFoundException, KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        KeyStore ks = loadCacertsFromPath(f);
        for (String certPath : newCertPath) {
            Certificate cert = loadNewCertificate(certPath);
            appendToCacerts(ks, cert);
        }
        exportNewCacerts(ks, f.getAbsolutePath(), pwd);
    }

}
