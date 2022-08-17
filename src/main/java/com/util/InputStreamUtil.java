package com.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class InputStreamUtil {

	public static void display(InputStream input) throws IOException {
		System.out.println(IOUtils.toString(input, StandardCharsets.UTF_8));
	}
}
