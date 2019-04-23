package com.apache.tomcat.servlet;

public interface Servlet {

  // public void init(ServletConfig config) throws ServletException;
  // public ServletConfig getServletConfig();
  // public String getServletInfo();
     public void service(HttpRequest req, HttpResponse res) throws Exception;
    
  // public void destroy();
}
