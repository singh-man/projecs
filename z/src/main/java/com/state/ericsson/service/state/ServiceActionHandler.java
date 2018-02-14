package com.state.ericsson.service.state;

public interface ServiceActionHandler {
	
	public void execute();
	
	
	public ServiceState doProvision();
	public ServiceState doUnProvision();
	public ServiceState resumeProvisioning();
	public ServiceState resumeUnProvisioning();
	public ServiceState doStart();
}
