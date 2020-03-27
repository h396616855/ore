package com.github.ore.boot.autoconfigure.config.yml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;

import com.github.ore.boot.autoconfigure.config.Constants;
import com.github.ore.boot.bootstrap.yml.OriginTrackedYamlLoader;

/**
 * Strategy to load '.yml' (or '.yaml') files into a {@link PropertySource}.
 *
 * @author Dave Syer
 * @author Phillip Webb
 * @author Andy Wilkinson
 */
public class YamlPropertySourceLoader implements PropertySourceLoader {

	@Override
	public String[] getFileExtensions() {
		return new String[] { "yml", "yaml" };
	}

	private void getConfigValue(String name, List<Map<String, Object>> loaded, int index) {
		Set<String> keySey = loaded.get(index).keySet();
		Iterator<String> it = keySey.iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object value = loaded.get(index).get(key);
			Matcher matchm = Pattern.compile(Constants.FILTER_WORD).matcher(key);
			if (matchm.find()) {
				System.out.println(name + " config >>> " + key + "=************");
			} else {
				System.out.println(name + " config >>> " + key + "=" + value);
			}
		}
	}

	@Override
	public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
		if (!ClassUtils.isPresent("org.yaml.snakeyaml.Yaml", null)) {
			throw new IllegalStateException(
					"Attempted to load " + name + " but snakeyaml was not found on the classpath");
		}

		List<Map<String, Object>> loaded = new OriginTrackedYamlLoader(resource).load();
		if (loaded.isEmpty()) {
			return Collections.emptyList();
		}

		Properties envProperties = PropertiesLoaderUtils.loadProperties(resource);
		String env = null;
		if(null != envProperties.get("active")) {
			env = envProperties.get("active").toString();
		}
		List<PropertySource<?>> propertySources = new ArrayList<>(loaded.size());
		if (null != env) {
			for (int i = 0; i < loaded.size(); i++) {
				Map<String, Object> map = loaded.get(i);
				if (null != map.get("spring.profiles")) {
					String active = map.get("spring.profiles").toString();
					if (env.equals(active)) {
						getConfigValue(name, loaded, i);
						break;
					}
				} else {
					getConfigValue(name, loaded, i);
				}
			}
		} else {
			getConfigValue(name, loaded, 0);
		}

		for (int i = 0; i < loaded.size(); i++) {
			Map<String, Object> map = loaded.get(i);
			if (null != map.get("spring.profiles")) {
				String active = map.get("spring.profiles").toString();
				if (env.equals(active)) {
					String documentNumber = (loaded.size() != 1) ? " (document #" + i + ")" : "";
					propertySources.add(new OriginTrackedMapPropertySource(name + documentNumber, loaded.get(i)));
					break;
				}
			} else {
				String documentNumber = (loaded.size() != 1) ? " (document #" + i + ")" : "";
				propertySources.add(new OriginTrackedMapPropertySource(name + documentNumber, loaded.get(i)));
			}

		}
		return propertySources;

	}

}
