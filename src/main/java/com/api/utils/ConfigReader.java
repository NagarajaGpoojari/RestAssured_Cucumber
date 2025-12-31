package com.api.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	private Properties properties;

	public ConfigReader(String fileName) {
		properties = new Properties();
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(fileName)) {
			if (input == null) {
				throw new FileNotFoundException("Property file '" + fileName + "' not found in classpath");
			}
			properties.load(input);
		} catch (IOException e) {
			throw new RuntimeException("Failed to load config file: " + fileName, e);
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
