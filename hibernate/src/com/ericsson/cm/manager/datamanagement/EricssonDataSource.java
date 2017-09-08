package com.ericsson.cm.manager.datamanagement;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

public class EricssonDataSource extends BasicDataSource {

	private String location;
	private String dialect;

	@PostConstruct
	private void init() throws IOException {
		org.springframework.core.io.Resource re = new ClassPathResource(location);
		Properties props = PropertiesLoaderUtils.loadProperties(re);
		createDataSource(props, props.getProperty("db.type"));
	}

	private synchronized void createDataSource(Properties props, String dbType) {
		setUsername(props.getProperty("db.user"));
		setPassword(props.getProperty("db.password"));

		DBProps dbProps = DBProps.valueOf(dbType.toUpperCase());

		String url = String.format(dbProps.url, props.getProperty("db.host"), props.getProperty("db.port"), props.getProperty("db.schema"));
		setUrl(url);
		
		setDriverClassName(dbProps.driver);
		
//		setMaxActive(getIntValue(props.getProperty("db.maxActive")));
		setInitialSize(getIntValue(props.getProperty("db.initialSize")));
		setMaxIdle(getIntValue(props.getProperty("db.maxIdle")));
		setMinIdle(getIntValue(props.getProperty("db.minIdle")));
		
		dialect = dbProps.hibernateDialect;
	}
	
	private int getIntValue(String s) {
		return Integer.parseInt(s);
	}

	private enum DBProps {
		POSTGRESQL("jdbc:postgresql://%s:%s/%s",
				"org.postgresql.Driver", 
				"org.hibernate.dialect.PostgreSQLDialect"), 
		
		ORACLE("jdbc:oracle:thin:@%s:%s:%s", 
				"oracle.jdbc.driver.OracleDriver",
				"org.hibernate.dialect.OracleDialect"),
		
		MYSQL("jdbc:mysql://%s:%s/%s", 
				"com.mysql.jdbc.Driver",
				"org.hibernate.dialect.MySQLDialect"),
		
		MYSQL5("jdbc:mysql://%s:%s/%s", 
				"com.mysql.jdbc.Driver",
				"org.hibernate.dialect.MySQL5Dialect"),
		
		SYBASEANYWHERE("jdbc:sybase:Tds:%s:%s?ServiceName=%s",
				"com.sybase.jdbc3.jdbc.SybDriver",
				"org.hibernate.dialect.SybaseAnywhereDialect"),
		
		SYBASEASE("jdbc:sybase:Tds:%s:%s/%s",
				"com.sybase.jdbc3.jdbc.SybDriver",
				"org.hibernate.dialect.SybaseDialect"),
		
		DERBY("","","");

		String url;
		String driver;
		String hibernateDialect;

		private DBProps(String url, String driver, String hibernateDialect) {
			this.url = url;
			this.driver = driver;
			this.hibernateDialect = hibernateDialect;
		}
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getDialect() {
		return dialect;
	}
}
