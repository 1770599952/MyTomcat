package com.urlservlet;

import com.apache.tomcat.annocation.WebServlet;
import com.apache.tomcat.servlet.HttpRequest;
import com.apache.tomcat.servlet.HttpResponse;
import com.apache.tomcat.servlet.HttpServlet;

@WebServlet("/secondServlet")
public class SecondServlet extends HttpServlet{

	@Override
	public void doGet(HttpRequest req, HttpResponse res) throws Exception {
		doPost(req, res);
	}

	@Override
	public void doPost(HttpRequest req, HttpResponse res) throws Exception {

		res.getWriter().write("1111");

	}

}
