package com.github.ore.boot.autoconfigure.context.custom;

import com.github.ore.framework.web.api.ResultEntity;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalResponseAdvice implements ResponseBodyAdvice<Object> {

	/**
	 * 不需要打开开关，通过Gson转换,使用返回的 Bean 类注解 @com.fasterxml.jackson.annotation.JsonProperty("channel_name") 字段
	 */
	private static boolean LOWER_CASE_WITH_UNDERSCORES = false;

	private boolean isBaseType(Object object) {
		Class<? extends Object> className = object.getClass();
		if (className.equals(java.lang.String.class) || className.equals(java.lang.Integer.class)
				|| className.equals(java.lang.Byte.class) || className.equals(java.lang.Long.class)
				|| className.equals(java.lang.Double.class) || className.equals(java.lang.Float.class)
				|| className.equals(java.lang.Character.class) || className.equals(java.lang.Short.class)
				|| className.equals(java.lang.Boolean.class)) {
			return true;
		}
		return false;
	}

	private Object translateName(Object body) {
		if (LOWER_CASE_WITH_UNDERSCORES) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
			Gson gson = gsonBuilder.create();
			if (body instanceof ResultEntity) {
				ResultEntity<?> result = (ResultEntity<?>) body;
				Object obj = result.getData();
				if (isBaseType(obj)) {
					return body;
				} else {
					ResultEntity<?> newResult = ResultEntity.data(obj);
					newResult.setCode(result.getCode());
					newResult.setMsg(result.getMsg());
					newResult.setTime(result.getTime());
					String json = gson.toJson(body);
					return gson.fromJson(json, Object.class);
				}
			} else {
				String json = gson.toJson(body);
				return ResultEntity.data(gson.fromJson(json, Object.class));
			}
		} else {
			if (body instanceof ResultEntity) {
				return body;
			} else {
				return ResultEntity.data(body);
			}
		}
	}

	@Override
	public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType,
			Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse) {

		// Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Gson gson = new Gson();
		String response = "response body   > {}";

		if (null == body) {
			log.info(response, body);
			// return ResultEntity
			return ResultEntity.data(null);
		}

		if (body instanceof ResultEntity) {
			log.info(response, gson.toJson(body));
			// return ResultEntity
			return translateName(body);
		} else {
			if (isBaseType(body)) {
				String jsonObject = gson.toJson(ResultEntity.data(body.toString()));
				log.info(response, jsonObject);
				// return string
				return jsonObject;
			} else {
				// 和swagger冲突，排除swagger的请求
				if (null != body && (0 < body.toString().indexOf("path=/") || 0 < body.toString().indexOf("swagger")
						|| 0 <= body.toString().indexOf("springfox.documentation.spring.web.json.Json"))) {
					log.info(response, body.toString());
					// return string
					return body;
				} else {
					log.info(response, gson.toJson(body));
					return translateName(body);
				}
			}

		}
	}
}
