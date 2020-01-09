package com.github.ore.boot.autoconfigure.context;

import com.github.ore.boot.autoconfigure.context.custom.GlobalExceptionHandler;
import com.github.ore.framework.web.api.ResultEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalExceptionAutoConfigure {
	
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
