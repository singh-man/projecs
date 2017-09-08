package com.web.spring.fileUpDown;

public enum KVPair {

	GET_FILE("getFile"),
	DOWNLOAD_PATH("downloadPath"),
	UPLOAD_PATH("uploadPath"),
	PASSWORD("password"),
	DOWN_UP_BEAN("downUpBean");
	
	
	private String pair;

	private KVPair(String pair) {
		this.pair = pair;
	}
	
	public String getValue(){
		return this.pair;
	}

}
