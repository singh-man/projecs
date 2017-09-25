package com.state.ericsson.service.state;
/*
 * Is Shared;
 * Defines operation and used by executor module i.e. MANAGER
 */
public enum ServiceAction {

	DO_PROVISION(new ServiceActionHandlerImpl()) {
		@Override
		public ServiceState execute() {
			return handler.doProvision();
		}
	},
	RESUME_PROVISION(new ServiceActionHandlerImpl()) {
		@Override
		public ServiceState execute() {
			return handler.resumeProvisioning();
		}
	},
	DO_UNPROVISION(new ServiceActionHandlerImpl()) {
		@Override
		public ServiceState execute() {
			return handler.doUnProvision();
		}
	},
	RESUME_UNPROVISION(new ServiceActionHandlerImpl()) {
		@Override
		public ServiceState execute() {
			return handler.resumeUnProvisioning();
		}
	},
	DO_START(new ServiceActionHandlerImpl()) {
		@Override
		public ServiceState execute() {
			return handler.doStart();
		}
	};
	
	ServiceActionHandler handler;
	
	private ServiceAction(ServiceActionHandler handler) {
		this.handler = handler;
	}
	
	public abstract ServiceState execute();

}
