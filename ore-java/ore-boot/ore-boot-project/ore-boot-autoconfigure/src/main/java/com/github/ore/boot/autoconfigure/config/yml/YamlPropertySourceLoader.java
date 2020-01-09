package com.github.ore.boot.autoconfigure.config.yml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.MarkerFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import com.github.ore.boot.bootstrap.yml.OriginTrackedYamlLoader;

import lombok.extern.slf4j.Slf4j;

/**
 * Strategy to load '.yml' (or '.yaml') files into a {@link PropertySource}.
 *
 * @author Dave Syer
 * @author Phillip Webb
 * @author Andy Wilkinson
 */
@Slf4j
public class YamlPropertySourceLoader implements PropertySourceLoader {

	private static String logMarker = "ore-boot";

	@Override
	public String[] getFileExtensions() {
		return new String[] { "yml", "yaml" };
	}

	@Override
	public List<PropertySource<?>> load(String name, Resource resource) throws IOException {
		if (!ClassUtils.isPresent("org.yaml.snakeyaml.Yaml", null)) {
			throw new IllegalStateException("Attempted to load " + name + " but snakeyaml was not found on the classpath");
		}

		List<Map<String, Object>> loaded = new OriginTrackedYamlLoader(resource).load();
		if (loaded.isEmpty()) {
			return Collections.emptyList();
		}

		List<PropertySource<?>> propertySources = new ArrayList<>(loaded.size());

		Set<String> keySey = loaded.get(0).keySet();
		Iterator<String> it = keySey.iterator();
		while (it.hasNext()) {
			String key = it.next();
			Object value = loaded.get(0).get(key);
			if (log.isDebugEnabled()) {
				log.info(MarkerFactory.getMarker(logMarker), resource.getFilename() + " config is >>> {}={}", key, value);
			}
		}

		for (int i = 0; i < loaded.size(); i++) {
			String documentNumber = (loaded.size() != 1) ? " (document #" + i + ")" : "";
			propertySources.add(new OriginTrackedMapPropertySource(name + documentNumber, loaded.get(i)));
		}
		return propertySources;

	}

}
