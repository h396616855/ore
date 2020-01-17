package com.github.ore.boot.autoconfigure.context.custom;

import com.github.ore.framework.web.api.ResultEntity;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

	private boolean isBaseType(Object object) {
		Class<? extends Object> className = object.getClass();
		if (className.equals(java.lang.String.class) || className.equals(java.lang.Integer.class) || className.equals(java.lang.Byte.class) || className.equals(java.lang.Long.class) || className.equals(java.lang.Double.class) || className.equals(java.lang.Float.class)
				|| className.equals(java.lang.Character.class) || className.equals(java.lang.Short.class) || className.equals(java.lang.Boolean.class)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

		// Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Gson gson = new Gson();
		String response = "response body   > {}";
		if (body instanceof ResultEntity) {
			log.info(response, gson.toJson(body));
			return body;
		} else {
			if (isBaseType(body)) {
				String jsonObject = gson.toJson(ResultEntity.data(body.toString()));
				log.info(response, jsonObject);
				return jsonObject;
			} else {

				// 和swagger冲突，排除swagger的请求
				if (null != body && (0 < body.toString().indexOf("path=/") 
						|| 0 < body.toString().indexOf("swagger") 
						|| 0 <= body.toString().indexOf("springfox.documentation.spring.web.json.Json"))) {
					log.info(response, body.toString());
					return body;
				} else {
					log.info(response, gson.toJson(body));
					return ResultEntity.data(body);
				}
			}

		}
	}
}
