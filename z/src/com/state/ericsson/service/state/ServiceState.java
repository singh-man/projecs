package com.state.ericsson.service.state;
/*
 * Is shared.
 * Kept exclusively for client i.e. GUI
 */
public enum ServiceState {

	NEW(ServiceAction.DO_PROVISION) {
		@Override
		public ServiceState execute(ServiceAction opertion) {
			return operate(opertion);
		}
	},
	PARTIAL_PROVISIONED(ServiceAction.DO_PROVISION, ServiceAction.DO_UNPROVISION) {
		@Override
		public ServiceState execute(ServiceAction opertion) {
			return operate(opertion);
		}
	},
	PROVISIONED(ServiceAction.DO_UNPROVISION, ServiceAction.DO_START) {
		@Override
		public ServiceState execute(ServiceAction opertion) {
			return operate(opertion);
		}
	};

	private ServiceAction[] serviceOpertions;
	private ServiceState(ServiceAction... serviceOpertions) {
		this.serviceOpertions = serviceOpertions;
	}
	private boolean isValidOperationForState(ServiceAction opertion) {
		for(ServiceAction opr : serviceOpertions) {
			if(opertion == opr)
				return true;
		}
		return false;
	}
	ServiceState operate(ServiceAction opertion) {
			if(isValidOperationForState(opertion))
				return opertion.execute();
			else 
				throw new RuntimeException("Operation not supported for this Service State");
	}
	public abstract ServiceState execute(ServiceAction opertion);
}
