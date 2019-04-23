package com.apache.tomcat.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;


public class WebConfig {

	private static WebConfig config = null;
	private Properties pro = null;

	static {
		try {
			config = new WebConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private WebConfig() {

//		if (null == config) {					
//			synchronized (Config.class) {
//				if (null == config) {
//					config = new Config();
//				}
//			}
//		}
		
		pro = new Properties();
		InputStream in = WebConfig.class.getClassLoader().getResourceAsStream(
				"web.properties");
		try {
			pro.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static WebConfig getInstance() {
		return config;
	}

	public String getProperty(String str) {

		try {
			str = pro.getProperty(str);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Set<Object> keySet() {
		return pro.keySet();
	}

	public static void main(String[] args) {
		WebConfig config = WebConfig.getInstance();
		System.out.println(config.getProperty("firstServlet-servlet-name"));
	}
}
