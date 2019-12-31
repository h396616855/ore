package com.github.ore.boot.autoconfigure.context;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import com.github.ore.boot.context.SpringContext;

@Configurable
public class SpringContextAutoConfigure {

	@Bean
	public SpringContext springContext() {
		return new SpringContext();
	}

}
