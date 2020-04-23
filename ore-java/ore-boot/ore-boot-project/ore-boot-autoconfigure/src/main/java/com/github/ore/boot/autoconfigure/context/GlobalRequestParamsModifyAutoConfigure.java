package com.github.ore.boot.autoconfigure.context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import com.github.ore.boot.autoconfigure.context.filter.ModifyParametersFilter;

@Configuration
@ConditionalOnProperty(name = "app.restful.columns.style", havingValue = "underline")
public class GlobalRequestParamsModifyAutoConfigure {

	@Autowired
	private ModifyParametersFilter modifyParametersFilter;

	@Bean
	public ModifyParametersFilter modifyParametersFilter() {
		return new ModifyParametersFilter();
	}

	@Bean
	public FilterRegistrationBean<ModifyParametersFilter> registerParamsServletFilter() {
		FilterRegistrationBean<ModifyParametersFilter> registration = new FilterRegistrationBean<ModifyParametersFilter>();
		registration.setFilter(modifyParametersFilter);
		registration.addUrlPatterns("/*");
		registration.setName("modifyParametersFilter");
		registration.setOrder(Ordered.LOWEST_PRECEDENCE);
		return registration;
	}
}
