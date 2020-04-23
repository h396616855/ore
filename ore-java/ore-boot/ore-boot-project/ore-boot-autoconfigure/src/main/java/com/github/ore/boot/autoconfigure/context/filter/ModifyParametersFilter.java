package com.github.ore.boot.autoconfigure.context.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ReadListener;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

/**
 * 功能：参数名称转换驼峰"_"下划线参数风格，只对在 url 上的拼接参数，和post form表单参数转换有效;</br>
 * 对payload提交的body提交的内容是无法转换,提交的body数据，只能通过在 Controller方法参数加 @RequestBody 接收数据,</br>
 * 实体类字段加@com.fasterxml.jackson.annotation.JsonProperty("aaa_bbb") 字段</br>
 * 
 */
@Slf4j
public class ModifyParametersFilter extends OncePerRequestFilter {

	private final static String underline = "_";
	private final static String paramNameNull = "";
	private final static String regx = "[_]\\w";
	private final static String utf8 = "UTF-8";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		ModifyParametersWrapper requestWrapper = new ModifyParametersWrapper(request);
		filterChain.doFilter(requestWrapper, response);
	}

	private class ModifyParametersWrapper extends HttpServletRequestWrapper {

		private Map<String, String[]> newMap = new HashMap<>();

		private final byte[] body;

		private GsonBuilder gsonBuilder = new GsonBuilder();

		private String getBodyString(final ServletRequest request) {
			StringBuilder sb = new StringBuilder();
			InputStream inputStream = null;
			BufferedReader reader = null;
			
			try {
				String line = null;
				inputStream = cloneInputStream(request.getInputStream());
				reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName(utf8)));
				while ((line = reader.readLine()) != null) {
					sb.append(line);
				}

				if (StringUtils.isNotBlank(sb.toString())) {

					// 使用gson这种方式转换 就不需要再 XxxVO.java/XXXQuery 类的字段加： @com.fasterxml.jackson.annotation.JsonProperty("某字段")注解
					Gson gson = gsonBuilder.create();
					JsonObject lowerCaseObject = new JsonObject();
					JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
					Iterator<String> iterator = jsonObject.keySet().iterator();

					while (iterator.hasNext()) {
						String key = iterator.next().toString();
						String lowerCasekey = underlineCamel(key);
						lowerCaseObject.add(lowerCasekey, jsonObject.get(key));
					}

					return gson.toJson(lowerCaseObject);
				}
			} catch (IOException e) {
				log.error("read body IOException", e);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						log.error("inputStream.close() IOException", e);
					}
				}
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						log.error("reader.close() IOException", e);
					}
				}
			}
			return sb.toString();
		}

		/**
		 * 下划线与小驼峰转换
		 * 
		 * @param name
		 * @return
		 */
		private String transformLowerCamelCase(String name) {
			if (name.contains(underline)) {
				String[] words = name.split(underline);
				if (null != words) {
					String key = words[0];
					for (int i = 1; i < words.length; i++) {
						key = key + words[i].substring(0, 1).toUpperCase().concat(words[i].substring(1).toLowerCase());
					}
					return key;
				}
			}
			return name;
		}

		/**
		 * 下划线转小驼峰
		 * 
		 * @param underline
		 * @return
		 */
		private String underlineCamel(String underline) {
			Pattern pattern = Pattern.compile(regx);
			String camel = underline.toLowerCase();
			Matcher matcher = pattern.matcher(camel);
			while (matcher.find()) {
				String w = matcher.group().trim();
				camel = camel.replace(w, w.toUpperCase().replace(underline, paramNameNull));
			}
			return camel;
		}

		private InputStream cloneInputStream(ServletInputStream inputStream) {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len;
			try {
				while ((len = inputStream.read(buffer)) > -1) {
					byteArrayOutputStream.write(buffer, 0, len);
				}
				byteArrayOutputStream.flush();
			} catch (IOException e) {
				log.error("cloneInputStream", e);
			}
			InputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
			return byteArrayInputStream;
		}

		public ModifyParametersWrapper(HttpServletRequest request) {
			super(request);
			Map<String, String[]> parameterMap = request.getParameterMap();
			Iterator<String> pNames = parameterMap.keySet().iterator();
			while (pNames.hasNext()) {
				String key = (String) pNames.next();
				String newKey = transformLowerCamelCase(key);
				newMap.put(newKey, parameterMap.get(key));
			}

			String sessionStream = getBodyString(request);
			body = sessionStream.getBytes(Charset.forName(utf8));
		}

		/**
		 * 获取所有参数名
		 */
		@Override
		public Enumeration<String> getParameterNames() {
			return Collections.enumeration(newMap.keySet());
		}

		/**
		 * 获取指定参数名的值，如果有重复的参数名，则返回第一个的值 接收一般变量 ，如text类型
		 */
		@Override
		public String getParameter(String name) {
			String[] results = newMap.get(name);
			if (results == null || results.length <= 0)
				return null;
			else {
				return results[0];
			}
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			return Collections.unmodifiableMap(newMap);
		}

		/**
		 * 获取指定参数名的所有值的数组，如：checkbox的所有数据 接收数组变量 ，如checkobx类型
		 */
		@Override
		public String[] getParameterValues(String name) {
			return newMap.get(name);
		}

		@Override
		public BufferedReader getReader() throws IOException {
			return new BufferedReader(new InputStreamReader(getInputStream()));
		}

		@Override
		public ServletInputStream getInputStream() throws IOException {

			final ByteArrayInputStream bais = new ByteArrayInputStream(body);

			return new ServletInputStream() {

				@Override
				public int read() throws IOException {
					return bais.read();
				}

				@Override
				public boolean isFinished() {
					return false;
				}

				@Override
				public boolean isReady() {
					return false;
				}

				@Override
				public void setReadListener(ReadListener readListener) {
				}
			};
		}
	}

}
