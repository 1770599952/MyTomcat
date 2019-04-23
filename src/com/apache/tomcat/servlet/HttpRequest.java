package com.apache.tomcat.servlet;

import java.io.IOException;
import java.io.InputStream;

public class HttpRequest {

	// 装饰者设计模式
	private InputStream is;

	private String method;

	private String url;

	public HttpRequest(InputStream is) {
		this.is = is;
		init();
	}

	private void init() {
		byte[] b = new byte[1024];
		int len = 0;
		StringBuffer sb = new StringBuffer();
		try {
			if((len = is.read(b)) > 0) {
				sb.append(new String(b, 0, len));
			}
			System.out.println(sb.toString());
			// 解析HTTP请求中的信息
			String info = sb.toString().split("\\n")[0];
			String method = info.split("\\s")[0];
			this.method = method;
			String requestUrl = info.split("\\s")[1];
			String url = requestUrl.split("\\?")[0];
			this.url = url;
			if(requestUrl.split("\\?").length > 1) {
				//QueryString 的解码字符集要么是 Header 中 ContentType 中定义的 Charset 要么就是默认的 ISO-8859-1，要使用 ContentType 中定义的编码就要设置 connector 的 <Connector URIEncoding=”UTF-8” useBodyEncodingForURI=”true”/> 中的 useBodyEncodingForURI 设置为 true。这个配置项的名字有点让人产生混淆，
				//它并不是对整个 URI 都采用 BodyEncoding 进行解码而仅仅是对 QueryString 使用 BodyEncoding 解码，这一点还要特别注意。
				String parameters = requestUrl.split("\\?")[1];
				// 对流中的字符进行解码
				String queryString = java.net.URLDecoder.decode(parameters, "utf-8"); 
				System.out.println(queryString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}
}
