package com.github.ore.boot.autoconfigure.config.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.slf4j.MarkerFactory;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertiesPropertySourceLoader implements PropertySourceLoader {

	private static String logMarker = "ore-boot";

	@Override
	public String[] getFileExtensions() {
		return new String[] { "properties" };
	}

	@Override
	public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
		List<PropertySource<?>> list = new ArrayList<PropertySource<?>>();
		Properties properties = PropertiesLoaderUtils.loadProperties(resource);
		Set<String> propertyNames = properties.stringPropertyNames();
		Iterator<String> it = propertyNames.iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = properties.getProperty(key);
			if (log.isDebugEnabled()) {
				log.info(MarkerFactory.getMarker(logMarker), resource.getFilename() + " config is --- > {}={}", key, value);
			}
		}
		PropertiesPropertySource proSource = new PropertiesPropertySource(name, properties);
		list.add(proSource);
		return list;
	}

}
