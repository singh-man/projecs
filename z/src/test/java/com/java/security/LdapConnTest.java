package com.java.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Hashtable;
import javax.naming.Context;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author msingh
 */
public class LdapConnTest {

    private Hashtable<String, Object> env;
    private LdapConn ldapConn;

    @Before
    public void setUp() {
        ldapConn = new LdapConn();
        env = new Hashtable<String, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testEricsson() {
        env.put(Context.PROVIDER_URL, "ldap://eapac.ericsson.se:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "CN=emmhssh,OU=EGI,OU=EGIS-CA,OU=Eusers,DC=eapac,DC=ericsson,DC=se");
        // env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put(Context.SECURITY_CREDENTIALS, getPassword());
        env.put("java.naming.ldap.version", "2");
        ldapConn.login(env, "DC=eapac,DC=ericsson,DC=se", "(CN=emmhssh)", "");
    }

    @Test
    public void testEricssonSSL() {
        env.put(Context.PROVIDER_URL, "ldaps://eapac.ericsson.se:636");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "CN=emmhssh,OU=EGI,OU=EGIS-CA,OU=Eusers,DC=eapac,DC=ericsson,DC=se");
        env.put(Context.SECURITY_PROTOCOL, "ssl");
		//env.put("com.sun.jndi.ldap.connect.pool.protocol","plain ssl");
        //env.put("java.naming.ldap.factory.socket", "MMSSLSocketFactory");
        env.put(Context.SECURITY_CREDENTIALS, getPassword());
        //System.setProperty("javax.net.ssl.trustStore", "/usr/lib/jvm/java-6-openjdk-amd64/jre/lib/security/cacerts");
        env.put("java.naming.ldap.version", "3");
        ldapConn.login(env, "DC=eapac,DC=ericsson,DC=se", "(CN=emmhssh)", "");
    }

    private void localSetup() {
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=openDS");
        env.put(Context.SECURITY_CREDENTIALS, "admin");
        env.put("com.sun.jndi.ldap.connect.pool", "true");
        System.setProperty("com.sun.jndi.ldap.connect.pool.initsize", "2");
        System.setProperty("javax.net.debug", "ssl");
        System.setProperty("com.sun.jndi.ldap.connect.pool.debug", "fine");
    }

    @Test
    public void testLocalNone() {
        localSetup();
        env.put(Context.PROVIDER_URL, "ldap://localhost:1389");
        ldapConn.login(env, "DC=eapac,DC=ericsson,DC=se", "(sn=singh)", "");
    }

    @Test
    public void testLocalSSL() {
        localSetup();
        env.put(Context.PROVIDER_URL, "ldaps://localhost:1636");
        env.put(Context.SECURITY_PROTOCOL, "ssl");
		//env.put("com.sun.jndi.ldap.connect.pool.protocol","plain ssl");
        //env.put("java.naming.ldap.factory.socket", "MMSSLSocketFactory");
        System.setProperty("javax.net.ssl.trustStore", "/home/emmhssh/dev/src/example/zzz/a.jks");
        ldapConn.login(env, "DC=eapac,DC=ericsson,DC=se", "(sn=singh)", "");
    }

    @Test
    public void testLocalTLS() {
        localSetup();
        env.put(Context.PROVIDER_URL, "ldaps://localhost:1389");
        env.put(Context.SECURITY_PROTOCOL, "tls");
        //env.put("java.naming.ldap.factory.socket", "MySSLSocketFactory");
        System.setProperty("javax.net.ssl.trustStore", "/home/emmhssh/dev/src/example/zzz/a.jks");
        ldapConn.login(env, "DC=eapac,DC=ericsson,DC=se", "(sn=singh)", "tls");
    }

    private String getPassword() {
        FileInputStream f = null;
        StringBuffer sb = new StringBuffer();
        try {
            f = new FileInputStream(new File("./abcd.txt"));

            byte[] buff = new byte[1024];
            int i = 0;

            while ((i = f.read(buff)) > 0) {
                sb.append(new String(buff));
            }
            f.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return sb.toString().trim();
    }
}
