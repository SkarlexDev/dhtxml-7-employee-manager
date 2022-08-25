package com.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

public class JsonToStringUtil {

	public static String format(HttpServletRequest req) throws IOException {
		return formatJson(req);
	}
	
	public static Long formatLong(HttpServletRequest req) throws IOException {
		return Long.parseLong(formatJson(req).replaceAll("\"", ""));
	}
	
	private static String formatJson(HttpServletRequest req) throws IOException {
		StringBuffer sb = new StringBuffer();
		String s;
		while ((s = req.getReader().readLine()) != null) {
			sb.append(s);
		}
		return sb.toString();
	}
}
