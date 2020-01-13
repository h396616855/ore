package com.github.ore.boot.autoconfigure.context;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.ore.boot.autoconfigure.context.custom.GlobalRequestBodyAdvice;
import com.github.ore.boot.autoconfigure.context.custom.GlobalRequestBodyFilter;

@Configuration
@ConditionalOnWebApplication
public class GlobalRequestAutoConfigure {

	/**
	 * 统一对请求参数打印处理
	 * 
	 * @return
	 */
	@Bean
	public GlobalRequestBodyFilter httpTraceLogFilter() {
		return new GlobalRequestBodyFilter();
	}

	/**
	 * 统一对请求响应的json串进行处理
	 * 
	 * @return
	 */
	@Bean
	public GlobalRequestBodyAdvice globalRequestBodyAdvice() {
		return new GlobalRequestBodyAdvice();
	}

}
