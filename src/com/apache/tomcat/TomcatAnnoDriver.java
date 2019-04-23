package com.apache.tomcat;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import com.apache.tomcat.annocation.WebServlet;

public class TomcatAnnoDriver {

	private Map<String, Class<?>> servletMap = new HashMap<String, Class<?>>();
	private final static String fileExtend = ".class";

	private void init() {
		loadServletComponents(getClasspath(), true);
	}

	private String getClasspath() {
		return this.getClass().getResource("/").toString().substring(6);
	}

	private void loadServletComponents(String filePath, boolean childPackage) {
		File file = new File(filePath);
		File[] childFiles = file.listFiles();
		for (File childFile : childFiles) {
			if (childFile.isDirectory()) {
				if (childPackage) {
					loadServletComponents(childFile.getPath(), childPackage);
				}
			} else {
				String childFilePath = childFile.getPath();				
				executeClassFilePath(childFilePath);
			}
		}
		return;
	}

	private void executeClassFilePath(String childFilePath) {
		// 1.如果是class文件
		if (!childFilePath.endsWith(fileExtend)) {
			return ;
		}
		String className = getClassNameBychildFilePath(childFilePath);
		// 2.判断class是否有webServlet注解
		try {
			Class<?> clazz = Class.forName(className);
			if (clazz.isAnnotationPresent(WebServlet.class)) {
				String url = clazz.getAnnotation(WebServlet.class).value();
				servletMap.put(url, clazz);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}

	private String getClassNameBychildFilePath(String childFilePath) {
		childFilePath = childFilePath.substring(childFilePath.indexOf(getClasspath()) + getClasspath().length() + 1,
				childFilePath.lastIndexOf("."));
		childFilePath = childFilePath.replace("\\", ".");
		return childFilePath;
	}

	public static void main(String[] args) {
		TomcatAnnoDriver driver = new TomcatAnnoDriver();
		driver.init();
		System.out.println(driver.servletMap);
		System.out.println();
	}

}
