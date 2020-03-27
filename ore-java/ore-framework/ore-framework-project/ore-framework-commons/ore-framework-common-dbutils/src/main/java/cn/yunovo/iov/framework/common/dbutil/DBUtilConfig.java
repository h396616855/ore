package cn.yunovo.iov.framework.common.dbutil;

import java.io.IOException;
import java.util.Properties;

import cn.yunovo.iov.framework.common.dbutil.db.DbConnectionProxy;



public class DBUtilConfig {
	
	private static String jdbc;
	
	private static Properties props = new Properties();
	
	
	public static void init() {
		try {
			props.load(DbConnectionProxy.class.getResourceAsStream(jdbc));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	

	public static String getConfig(String config){
		return props.getProperty(config);
		
	}


	public static String getJdbc() {
		return jdbc;
	}


	public static void setJdbc(String jdbc) {
		DBUtilConfig.jdbc = jdbc;
	}
}
