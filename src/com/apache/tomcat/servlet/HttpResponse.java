package com.apache.tomcat.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class HttpResponse {

	// 装饰者设计模式
	private OutputStream os;

	public HttpResponse(OutputStream os) {
		this.os = os;
	}
	
	public OutputStream getOutputstream() {
		return os;
	}
	
	public Writer getWriter() {
		OutputStreamWriter osw = new OutputStreamWriter(os);
		return osw;
	}
	
	public void close() {
		try {
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
