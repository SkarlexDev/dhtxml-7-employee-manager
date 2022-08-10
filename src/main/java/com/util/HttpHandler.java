package com.util;

import javax.servlet.http.HttpServletResponse;

public class HttpHandler {

    public static void handle(HttpServletResponse res) {
        res.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        res.setHeader("Pragma", "no-cache");
        res.setDateHeader("Expires", 0);
    }
}
