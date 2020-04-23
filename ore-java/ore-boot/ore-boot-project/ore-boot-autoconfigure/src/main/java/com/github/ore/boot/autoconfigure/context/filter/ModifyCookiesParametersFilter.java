package com.github.ore.boot.autoconfigure.context.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class ModifyCookiesParametersFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// 修改cookie
		ModifyHttpServletRequestWrapper mParametersWrapper = new ModifyHttpServletRequestWrapper(request);
		String token = request.getHeader("token");
		if (token != null && !"".equals(token)) {
			mParametersWrapper.putCookie("JSESSIONID", token);
		}
		// finish
		filterChain.doFilter(mParametersWrapper, response);
	}

	private class ModifyHttpServletRequestWrapper extends HttpServletRequestWrapper {

		private Map<String, String> mapCookies;

		public ModifyHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
			this.mapCookies = new HashMap<>();
		}

		void putCookie(String name, String value) {
			this.mapCookies.put(name, value);
		}

		public Cookie[] getCookies() {
			HttpServletRequest request = (HttpServletRequest) getRequest();
			Cookie[] cookies = request.getCookies();
			if (mapCookies == null || mapCookies.isEmpty()) {
				return cookies;
			}
			if (cookies == null || cookies.length == 0) {
				List<Cookie> cookieList = new LinkedList<>();
				for (Map.Entry<String, String> entry : mapCookies.entrySet()) {
					String key = entry.getKey();
					if (key != null && !"".equals(key)) {
						cookieList.add(new Cookie(key, entry.getValue()));
					}
				}
				if (cookieList.isEmpty()) {
					return cookies;
				}
				return cookieList.toArray(new Cookie[cookieList.size()]);
			} else {
				List<Cookie> cookieList = new ArrayList<>(Arrays.asList(cookies));
				for (Map.Entry<String, String> entry : mapCookies.entrySet()) {
					String key = entry.getKey();
					if (key != null && !"".equals(key)) {
						for (int i = 0; i < cookieList.size(); i++) {
							if (cookieList.get(i).getName().equals(key)) {
								cookieList.remove(i);
							}
						}
						cookieList.add(new Cookie(key, entry.getValue()));
					}
				}
				return cookieList.toArray(new Cookie[cookieList.size()]);
			}
		}
	}
}
