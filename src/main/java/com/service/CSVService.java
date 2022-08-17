package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface CSVService {
	List<String> getAllVacantion() throws IOException;
	String upload(InputStream fileInputStream) throws IOException;
}
