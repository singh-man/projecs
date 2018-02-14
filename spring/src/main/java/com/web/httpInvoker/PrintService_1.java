package com.web.httpInvoker;

import java.util.concurrent.Future;

public interface PrintService_1 {
	
	public String print(String msg);
	
	public void execute();
	
	public void asyncExecute();
	
	public Future<String> asyncExecuteWithReturn();

}
