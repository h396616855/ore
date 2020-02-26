package com.github.ore.boot.autoconfigure.config.properties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.github.ore.boot.autoconfigure.config.Constants;

public class PropertiesPropertySourceLoader implements PropertySourceLoader {

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
			Matcher matchm = Pattern.compile(Constants.FILTER_WORD).matcher(key);
			if (matchm.find()) {
				System.out.println(name + " config >>> " + key + "=************");
			} else {
				System.out.println(name + " config >>> " + key + "=" + value);
			}
		}
		PropertiesPropertySource proSource = new PropertiesPropertySource(name, properties);
		list.add(proSource);
		return list;
	}

}
