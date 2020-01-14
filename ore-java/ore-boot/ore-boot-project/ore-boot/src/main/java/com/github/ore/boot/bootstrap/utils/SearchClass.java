package com.github.ore.boot.bootstrap.utils;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import gnu.regexp.REException;

public class SearchClass {
	private static final String fileSeparator = System.getProperty("file.separator");

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static String searchClassName(String className, String directories) throws REException {
		String lSearchTerm = className;
		Set lDirectories = new TreeSet();
		lDirectories.add(new File(directories));
		JarSearch lSearch = new JarSearch(lSearchTerm, lDirectories);
		lSearch.setRecurseArchives(true);
		lSearch.setRecurseDirectories(true);
		System.out.println("Searching for '" + lSearchTerm + "' ...");
		List lResults = lSearch.execute();
		System.out.println("There are " + lResults.size() + " matches:");
		Iterator i = lResults.iterator();
		while (i.hasNext()) {
			String lMatch = (String) i.next();
			if (null != lMatch) {
				return lMatch;
			}
		}
	
		return null;
	}

	public static String searchPackage(String className, String directories) {
		String classPath;
		String packageName = null;
		try {
			classPath = searchClassName(className, directories);
			if (null == classPath) {
				return null;
			}

			System.out.println(classPath);
			String classesSeparator = "classes" + fileSeparator;
			int i = classPath.indexOf(classesSeparator);
			classPath = classPath.substring(i, classPath.length());
			packageName = classPath.replace(fileSeparator, ".");
			classPath = packageName.substring(packageName.indexOf(".") + 1, packageName.length());
			if (classPath.endsWith(".class")) {
				int index = classPath.lastIndexOf(".");
				if (index != -1) {
					packageName = classPath.substring(0, classPath.length() - 6);
				}
			} else {
				return null;
			}
		} catch (REException e) {
			e.printStackTrace();
		}
		return packageName;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String searchJar(String starterClass, String jarFile) {
		Set lDirectories = new TreeSet();
		lDirectories.add(new File(jarFile));

		JarSearch lSearch;
		try {
			lSearch = new JarSearch(starterClass, lDirectories);

			lSearch.setRecurseArchives(false);
			lSearch.setRecurseDirectories(true);

			System.out.println("Searching for '" + starterClass + "' ...");
			List lResults = lSearch.execute();

			System.out.println("There are " + lResults.size() + " matches:");
			Iterator i = lResults.iterator();
			while (i.hasNext()) {
				String lMatch = (String) i.next();
				String bootClass = "BOOT-INF/classes/";
				int start = lMatch.indexOf(bootClass);
				String className = lMatch.substring(start,lMatch.length());
				className = className.replace(bootClass, "");
				className = className.replace("/", ".");
				className = className.replace(".class", "");
				starterClass = className;
				System.out.println(lMatch);
				return starterClass;
			}
		} catch (REException e) {
			e.printStackTrace();
		}
		return starterClass;
	}

	public static void main(String[] args) {
		System.out.println(searchPackage("JarSearchTest", SearchClass.class.getResource("/").getPath()));
	}

}
