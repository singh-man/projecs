package com.ericsson.cm.manager.datamanagement;

import com.ericsson.cm.common.exception.MMException;

public interface DemoTrasaction {

	public boolean saveSuccessOperation(long collId, String serverGroupName) throws MMException;
}

