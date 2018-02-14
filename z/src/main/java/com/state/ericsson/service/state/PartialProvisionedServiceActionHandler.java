package com.state.ericsson.service.state;

public interface PartialProvisionedServiceActionHandler extends UnProvisionServiceActionHandler {
	
	public ServiceState resumeProvision();
	
	public ServiceState resumeUnProvision();
	
}
