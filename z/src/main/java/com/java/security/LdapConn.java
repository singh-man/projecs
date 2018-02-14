package com.java.security;

import java.io.IOException;
import java.util.Hashtable;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.StartTlsRequest;
import javax.naming.ldap.StartTlsResponse;
import javax.net.ssl.SSLSession;

public class LdapConn {

    public void login(Hashtable<String, Object> env, String path,
            String searchFilter, String protocol) {

        try {
            // Create initial context
            LdapContext ctx = new InitialLdapContext(env, null);

            if (protocol.equals("tls")) {
                StartTlsResponse tls = (StartTlsResponse) ctx.extendedOperation(new StartTlsRequest());
                try {
                    SSLSession sess = tls.negotiate();
                    oper(path, searchFilter, ctx);
                    // Stop TLS
                    tls.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                oper(path, searchFilter, ctx);
            } else {
                oper(path, searchFilter, ctx);
            }

            ctx.close();

        } catch (NamingException e) {
            e.printStackTrace();
        } finally {

        }
    }

    private void oper(String path, String searchFilter, DirContext ctx)
            throws NamingException {
        SearchControls searchControls = new SearchControls();
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        NamingEnumeration<SearchResult> results = ctx.search(path,
                searchFilter, searchControls);
        while (results.hasMoreElements()) {
            SearchResult searchResult = (SearchResult) results.nextElement();
            Attribute attribute = searchResult.getAttributes().get("cn");
            System.out.println("==========" + attribute.get());
        }
    }

//    public static void main(String[] args) {
//        LdapConn l = new LdapConn();
//        l.setUp();
//        l.testLocalSSL();
//    }
}
