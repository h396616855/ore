package com.github.ore.boot.autoconfigure.context;

import com.github.ore.framework.web.api.ResultEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalAutoConfigure {
	
	/**
	 * 统一对请求响应的json串进行处理
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnClass(ResultEntity.class)
	public GlobalRequestBodyAdvice globalRequestBodyAdvice() {
		return new GlobalRequestBodyAdvice();
	}
	
	/**
	 * 统一restful返回值及统一处理
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnClass(ResultEntity.class)
	public GlobalResponseAdvice globalResponseAdvice() {
		return new GlobalResponseAdvice();
	}
	
	/**
	 * 对异常（包括系统异常与业务逻辑异常）进行统一处理
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnClass(ResultEntity.class)
	public GlobalExceptionHandler globalExceptionHandler() {
		return new GlobalExceptionHandler();
	}
}
