package com.github.ore.boot.autoconfigure.context.custom;

import com.github.ore.framework.web.api.ResultEntity;
import com.github.ore.framework.web.ui.ColumnsStyleEnum;

import org.springframework.beans.factory.annotation.Value;
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

	private static Boolean LOWER_CASE_WITH_UNDERSCORES;

	/**
	 * 通过Gson转换字段风格,</br>
	 * 使用返回的 BeanVO 类属性字段就不需要加注解 @com.fasterxml.jackson.annotation.JsonProperty("aaa_bbb") 字段
	 */
	@Value("${app.restful.columns.style:lowerCamelCase}")
	private String columnsStyle;

	private boolean isBaseType(Object object) {
		Class<? extends Object> className = object.getClass();
		if (className.equals(java.lang.String.class) 
				|| className.equals(java.lang.Integer.class) 
				|| className.equals(java.lang.Byte.class) 
				|| className.equals(java.lang.Long.class) 
				|| className.equals(java.lang.Double.class)
				|| className.equals(java.lang.Float.class) 
				|| className.equals(java.lang.Character.class) 
				|| className.equals(java.lang.Short.class) 
				|| className.equals(java.lang.Boolean.class)) {
			return true;
		}
		return false;
	}

	private Object translateName(Object body) {
		
		if (null == LOWER_CASE_WITH_UNDERSCORES) {
			LOWER_CASE_WITH_UNDERSCORES = ColumnsStyleEnum.UNDERLINE.toString().equals(columnsStyle) ? true : false;
		}

		if (LOWER_CASE_WITH_UNDERSCORES) {
			GsonBuilder gsonBuilder = new GsonBuilder();
			// 使用gson这种方式转换 就不需要再 XxxVO.java 类的字段加： @com.fasterxml.jackson.annotation.JsonProperty("某字段")注解
			gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
			Gson gson = gsonBuilder.create();
			if (body instanceof ResultEntity) {
				ResultEntity<?> result = (ResultEntity<?>) body;
				Object obj = result.getData();
				if (isBaseType(obj)) {
					return body;
				} else {
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
	public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

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
				if (null != body && (0 < body.toString().indexOf("path=/") || 0 < body.toString().indexOf("swagger") || 0 <= body.toString().indexOf("springfox.documentation.spring.web.json.Json"))) {
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
