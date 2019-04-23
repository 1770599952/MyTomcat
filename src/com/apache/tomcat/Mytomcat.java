package com.apache.tomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import com.apache.tomcat.config.WebConfig;
import com.apache.tomcat.servlet.HttpRequest;
import com.apache.tomcat.servlet.HttpResponse;
import com.apache.tomcat.servlet.HttpServlet;

public class Mytomcat {
	
	// 监听端口
	private static int port = 8080;
	// 服务器
	private static ServerSocket server;
	// 映射
	private Map<String,HttpServlet> servletMapping = new HashMap<String,HttpServlet>();
	// 解析web配置
	WebConfig config = WebConfig.getInstance();

	// 1.加载配置文件，初始化Servletmapping
	private void initServer() {	
		// 启动Server
		try {
			server = new ServerSocket(port);
			System.out.println("开始在8080端口监听：");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void initMapInfo() {
		// (1).遍历这个web文件
		for (Object keySet : config.keySet()) {
			try {
			// 根据key得到每一个value,然后反射生成对应得Servlet
			String key = keySet.toString();
			// 如果是映射Servlet的配置
			if(key.endsWith("mapping")) {
				String servletName = config.getProperty(key);
				String reqUrl = key.replaceAll("mapping", "name");
				String url = config.getProperty(reqUrl);
				HttpServlet servlet = (HttpServlet)Class.forName(servletName).newInstance();
				servletMapping.put(url, servlet);
			}
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	// 2.等待用户请求
	private void process(Socket socket) {

			try {
				// 3.获取socket对象，封装为request和response
				OutputStream os = socket.getOutputStream();
				InputStream is = socket.getInputStream();
				
				HttpRequest request = new HttpRequest(is);
				HttpResponse response = new HttpResponse(os);
				
				String url = request.getUrl();
				// 4.实现动态调用doGet和doPost方法
				if(servletMapping.containsKey(url)) {
					HttpServlet servlet = servletMapping.get(url);
					servlet.service(request, response);
				}else {
					response.getOutputstream().write("404-NOT-FOUND!".getBytes());
				}
				
				os.flush();
				os.close();
				socket.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	public static void main(String[] args) {
		Mytomcat tomcat = new Mytomcat();
		tomcat.initServer();
		tomcat.initMapInfo();
		tomcat.initProcess();
	}

	private void initProcess() {
		while(true) {
			Socket socket = null;
			try {
				socket = server.accept();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			process(socket);
		}
	}
}
