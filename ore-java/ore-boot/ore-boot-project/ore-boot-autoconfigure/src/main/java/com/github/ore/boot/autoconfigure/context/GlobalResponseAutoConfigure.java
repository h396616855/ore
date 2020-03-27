package com.github.ore.boot.autoconfigure.context;

import com.github.ore.boot.autoconfigure.context.custom.GlobalResponseAdvice;
import com.github.ore.framework.web.api.ResultEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalResponseAutoConfigure {
	
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
	
}
