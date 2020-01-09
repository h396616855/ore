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
		if (className.equals(java.lang.String.class) || className.equals(java.lang.Integer.class) || className.equals(java.lang.Byte.class) || className.equals(java.lang.Long.class) || className.equals(java.lang.Double.class) || className.equals(java.lang.Float.class) || className.equals(java.lang.Character.class) || className.equals(java.lang.Short.class) || className.equals(java.lang.Boolean.class)) {
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

		String url = serverHttpRequest.getURI().toString();
		String address = serverHttpRequest.getRemoteAddress().toString();
		String localAddress = serverHttpRequest.getLocalAddress().toString();
		log.info("{} access path >>> {}", address, url);

		if (body instanceof ResultEntity) {
			log.info("{} response content >>> {}", localAddress, body.toString());
			return body;
		} else {
			if (isBaseType(body)) {
				ResultEntity<String> result = new ResultEntity<String>();
				// Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
				Gson gson = new Gson();
				result.setData(body.toString());
				String jsonObject = gson.toJson(result);
				log.info("{} response content >>> {}", localAddress, jsonObject);
				return jsonObject;
			} else {
				ResultEntity<Object> result = new ResultEntity<Object>();

				// 和swagger冲突，排除swagger的请求
				if (null != body && (0 < body.toString().indexOf("path=/") 
						|| 0 < body.toString().indexOf("swagger") 
						|| 0 <= body.toString().indexOf("springfox.documentation.spring.web.json.Json"))) {
					log.info("{} response content >>> {}", localAddress, body.toString());
					return body;
				} else {
					result.setData(body);
					log.info("{} response content >>> {}", localAddress, body.toString());
					return result;
				}
			}

		}
	}
}
