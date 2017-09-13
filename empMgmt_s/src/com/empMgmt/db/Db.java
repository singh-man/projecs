package com.empMgmt.db;


public enum Db {

	MYSQL("com.mysql.jdbc.Driver", 
			"jdbc:mysql://localhost:3306/emp", 
			"root", 
			""),

	DERBY("org.apache.derby.jdbc.EmbeddedDriver", 
			"jdbc:derby:emp;create=true", 
			"",
			""),

	H2("org.h2.Driver",
			"jdbc:h2:~/h2/emp",
			"emp",
			"h2Admin");

	private Db(String driverClass, String url, String username,
			String password) {
		this.driverClass = driverClass;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	private String driverClass;
	private String url;
	private String username;
	private String password;
	
	public String getDriverClass() {
		return driverClass;
	}
	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
