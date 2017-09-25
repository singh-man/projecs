package com.state.ericsson.service.state;

public interface ProvisionServiceActionHandler extends UnProvisionServiceActionHandler {
	
	public ServiceState doStart();
	
}
