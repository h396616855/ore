package com.github.ore.boot.autoconfigure.context.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ModifyHeaderParametersFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		request.setAttribute("hdd", "tttt");
		HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(request);
		requestWrapper.addHeader("realm", "test");
		log.info("header-->{}", getHeadKeyAndValue(request));
		filterChain.doFilter(requestWrapper, response);
	}

	private Map<String, String> getHeadKeyAndValue(HttpServletRequest httpRequest) {
		Map<String, String> header = new HashMap<>();
		Enumeration<String> headerNames = httpRequest.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String nextElement = headerNames.nextElement();
			header.put(nextElement, httpRequest.getHeader(nextElement));
		}
		return header;
	}

	public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {

		public HeaderMapRequestWrapper(HttpServletRequest request) {
			super(request);
		}

		private Map<String, String> headerMap = new HashMap<>();

		public void addHeader(String name, String value) {
			headerMap.put(name, value);
		}

		@Override
		public String getHeader(String name) {
			log.info("getHeader --->{}", name);
			String headerValue = super.getHeader(name);
			if (headerMap.containsKey(name)) {
				headerValue = headerMap.get(name);
			}
			return headerValue;
		}

		@Override
		public Enumeration<String> getHeaderNames() {
			List<String> names = Collections.list(super.getHeaderNames());
			for (String name : headerMap.keySet()) {
				names.add(name);
			}
			return Collections.enumeration(names);
		}

		@Override
		public Enumeration<String> getHeaders(String name) {
			log.info("getHeaders --->>>>>>{}", name);
			List<String> values = Collections.list(super.getHeaders(name));
			log.info("getHeaders --->>>>>>{}", values);
			if (headerMap.containsKey(name)) {
				log.info("getHeaders --->{}", headerMap.get(name));
				values = Arrays.asList(headerMap.get(name));
			}
			return Collections.enumeration(values);
		}
	}
}
