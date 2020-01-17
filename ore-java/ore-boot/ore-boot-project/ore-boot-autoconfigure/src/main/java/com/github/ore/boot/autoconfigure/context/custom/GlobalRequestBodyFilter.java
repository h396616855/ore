package com.github.ore.boot.autoconfigure.context.custom;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalRequestBodyFilter extends OncePerRequestFilter implements Ordered {
	private static final String FAVICON = "/favicon.ico";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		if (FAVICON.equals(request.getRequestURI())) {
			filterChain.doFilter(request, response);
			return;
		}
		Gson gson = new Gson();
		log.info("access path to  > {}", request.getRequestURL());
		if (!request.getParameterMap().isEmpty()) {
			log.info("request parames > {}", gson.toJson(request.getParameterMap()));
		}
		filterChain.doFilter(request, response);
		return;
	}

	@Override
	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE - 10;
	}

}
