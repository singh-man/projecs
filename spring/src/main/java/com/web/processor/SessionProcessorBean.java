/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.processor;

/**
 *
 * Test with:
 * http://localhost:8080/zzz_spring/processorServlet?name=manish
 * 
 * @author emmhssh
 */
@SessionInjectedBean
public class SessionProcessorBean {
    
    private int sessionBean;
    private ProvidedBean providedBean;
    private ProvidedBeanSingleton providedBeanSingleton;

    public void setSessionBean(int sessionBean) {
        this.sessionBean = sessionBean;
    }

    public int getSessionBean() {
        return sessionBean;
    }

	public ProvidedBean getProvidedBean() {
		return providedBean;
	}
	
	@Provided
	public void setProvidedBean(ProvidedBean providedBean) {
		this.providedBean = providedBean;
	}

	public ProvidedBeanSingleton getProvidedBeanSingleton() {
		providedBeanSingleton.setDesc(providedBeanSingleton.getDesc() + " : " + sessionBean++);
		return providedBeanSingleton;
	}

	@Provided
	public void setProvidedBeanSingleton(ProvidedBeanSingleton providedBeanSingleton) {
		this.providedBeanSingleton = providedBeanSingleton;
	}
    
    
}
