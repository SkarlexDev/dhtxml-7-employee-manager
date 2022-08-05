package com.util;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;

public class JsonToStringUtil {

    public static String format(HttpServletRequest req) throws IOException {
        StringBuffer sb = new StringBuffer();
        String s;
        while ((s = req.getReader().readLine()) != null) {
            sb.append(s);
        }
        return sb.toString();
    }
}
