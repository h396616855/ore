package com.github.ore.boot.bootstrap.utils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	private Properties props;

	public PropertiesUtil(String fileName) {
		readProperties(fileName);
	}

	public PropertiesUtil(InputStream in) {
		readProperties(in);
	}

	private void readProperties(String fileName) {
		try {
			props = new Properties();
			FileInputStream fis = new FileInputStream(fileName);
			props.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readProperties(InputStream in) {
		try {
			props = new Properties();
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProperty(String key) {
		return props.getProperty(key);
	}
}
