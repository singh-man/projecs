package com.component;

public class Address {
	
	private int hno;
	private String city;
	
	public Address() {}
	public Address(int hno, String city) {
		this.hno = hno;
		this.city = city;
	}

	public int getHno() {
		return hno;
	}
	public void setHno(int hno) {
		this.hno = hno;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	

}
