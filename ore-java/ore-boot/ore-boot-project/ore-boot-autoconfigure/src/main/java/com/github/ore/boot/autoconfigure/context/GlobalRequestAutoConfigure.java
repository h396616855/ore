package com.github.ore.boot.autoconfigure.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.ore.boot.autoconfigure.context.custom.GlobalRequestBodyAdvice;

@Configuration
public class GlobalRequestAutoConfigure {
	
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
