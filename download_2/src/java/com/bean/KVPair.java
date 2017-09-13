package com.bean;

public enum KVPair {

	GET_FILE("getFile"),
	DOWNLOAD_PATH("downloadPath"),
	UPLOAD_PATH("uploadPath"),
	PASSWORD("password"),
	C_BEAN("cBean"),
	S_BEAN("sBean"),
	R_BEAN("rBean");
	
	
	private String pair;

	private KVPair(String pair) {
		this.pair = pair;
	}
	
	public String getValue(){
		return this.pair;
	}

}
