package com.urlservlet;

import com.apache.tomcat.annocation.WebServlet;
import com.apache.tomcat.servlet.HttpRequest;
import com.apache.tomcat.servlet.HttpResponse;
import com.apache.tomcat.servlet.HttpServlet;

@WebServlet("/firstServlet")
public class FirstServlet extends HttpServlet{

	@Override
	public void doGet(HttpRequest req, HttpResponse res) throws Exception {
		doPost(req, res);
		
	}

	@Override
	public void doPost(HttpRequest req, HttpResponse res) throws Exception {
	//	res.getOutputstream().write("一生何求！".getBytes());
		System.out.println("doPost方法执行！");
		res.getOutputstream().write("First-Servlet!".getBytes());
	
	}

}
