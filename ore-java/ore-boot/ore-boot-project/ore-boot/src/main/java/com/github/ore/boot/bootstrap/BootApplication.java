package com.github.ore.boot.bootstrap;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.lang3.ClassUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ore.boot.bootstrap.env.EnvConfigurer;
import com.github.ore.boot.bootstrap.starter.AbsBootStarter;
import com.github.ore.boot.bootstrap.utils.PropertiesUtil;
import com.github.ore.boot.bootstrap.utils.ResourceUtils;
import com.github.ore.boot.bootstrap.utils.SearchClass;

/**
 * sh或者bat脚本引导启动类,适合spring-boot的app打成war包，打成jar不支持
 */
public class BootApplication {

	private final static Logger logger = LoggerFactory.getLogger(BootApplication.class);

	private final static String START_CLASSNAMEKEY = "ore.starter.class-name";

	private static void start(String[] args) {

		String starterClass = null;
		String configFileName = null;

		try {
			InputStream is = ResourceUtils.getResourceAsStream(BootApplication.class.getClassLoader(), "META-INF/starter.properties");
			PropertiesUtil propertiesUtil = new PropertiesUtil(is);
			if (null == starterClass) {
				starterClass = propertiesUtil.getProperty(START_CLASSNAMEKEY);
			}

			String path = null;
			URL url = BootApplication.class.getResource("/");
			if (null != url) {
				path = BootApplication.class.getResource("/").getPath();
			} else {
				// windows 环境下url 为null,所以需要通过 main() 传递 classpath 上下文路径
				path = EnvConfigurer.context;
			}
			logger.error("****[ " + path + " ]**** search path!");

			// 应用打成war包方式查找执行类
			String className = SearchClass.searchPackage(starterClass, path);
			if (null == className) {

				// 应用打成Jar包方式查找执行类
				// Windows OS
				String jarFile = path.replaceAll("jar!/BOOT-INF/classes!/", "jar").replaceFirst("file:/", "");
				className = SearchClass.searchJar(starterClass, jarFile);
				if (null == className) {
					logger.error("****[ " + starterClass + " ]**** class is not existence! Server stop!");
					System.exit(0);
				}

			}
			
			logger.info(" >>> from {},search to {}", path, className);
			AbsBootStarter bootStarter = (AbsBootStarter) ClassUtils.getClass(className).newInstance();
			bootStarter.run(args);
		} catch (InstantiationException e) {
			logger.error(e.toString());
		} catch (IllegalAccessException e) {
			logger.error(e.toString());
		} catch (ClassNotFoundException e) {
			logger.error(e.toString());
		} catch (LinkageError e) {
			logger.error(e.toString());
		} catch (IOException e) {
			logger.error("****[ " + configFileName + " ]**** file not existence! Server stop!");
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		if (null != args) {
			for (int i = 0; i < args.length; i++) {
				String envArgs = args[i];
				if (envArgs.indexOf("context=") > -1) {
					String[] envs = envArgs.split("=");
					EnvConfigurer.context = envs[1];
					logger.info("context=" + EnvConfigurer.context);
				}
				if (envArgs.indexOf("env=") > -1) {
					String[] envs = envArgs.split("=");
					EnvConfigurer.env = envs[1];
					logger.warn("env=" + EnvConfigurer.env);
				}
				if (envArgs.indexOf("--spring.profiles.active") > -1) {
					String[] envs = envArgs.split("=");
					EnvConfigurer.env = envs[1];
					logger.warn("--spring.profiles.active=" + EnvConfigurer.env);
				}
			}
		}
		start(args);
	}

}
