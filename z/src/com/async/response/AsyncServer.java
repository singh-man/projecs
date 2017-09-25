package com.async.response;

import java.util.HashMap;
import java.util.Map;

public class AsyncServer {
	
	private Map<String, OnCompletion> callBacks;
	private double i;
	
	public AsyncServer() {
		callBacks = new HashMap<String, OnCompletion>();
		sendResponse();
	}
	
	public void performAsync(String pub, OnCompletion response) {
		callBacks.put(pub, response);
		i = Math.random();
	}
	
	public void sendResponse() {
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				OnCompletion res = callBacks.get("a");
				res.performTask(i);
			}
		});
		t.start();
	}

}
 