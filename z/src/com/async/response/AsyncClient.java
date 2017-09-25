package com.async.response;

public class AsyncClient {
	
	private AsyncServer a;
	public AsyncClient() {
		a = new AsyncServer();
	}
	
	public void toServer() {
		System.out.println("AsyncClient.toServer() : " + "sending to server");

		a.performAsync("a", new AbstractOnCompletion() {
			
			@Override
			public void performTask(Object o) {
				System.out
						.println("AsyncClient.toServer().new AbstractOnCompletion() {...}.taskDone() : " 
				+ "after completion : " + o.toString());
			}
		});
		System.out.println("AsyncClient.toServer() : " + "sending server done");
	}

}
