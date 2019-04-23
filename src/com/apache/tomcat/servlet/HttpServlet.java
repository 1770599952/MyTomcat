package com.apache.tomcat.servlet;

public abstract class HttpServlet implements Servlet{
	
	@Override
	public void service(HttpRequest req, HttpResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("GET")) {
			doGet(req, res);
		}else {
			doPost(req, res);
		}
	}

	public abstract void doGet(HttpRequest req, HttpResponse res) throws Exception;
	
	public abstract void doPost(HttpRequest req, HttpResponse res) throws Exception;
}
