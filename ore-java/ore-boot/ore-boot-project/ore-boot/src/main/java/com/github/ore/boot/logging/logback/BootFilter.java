package com.github.ore.boot.logging.logback;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * 日志过滤器
 */
@SuppressWarnings("rawtypes")
public class BootFilter extends Filter {

	@Override
	public FilterReply decide(Object eventObject) {
		LoggingEvent event = (LoggingEvent) eventObject;

		if (event.getMessage().contains("ore-boot")) {
			return FilterReply.ACCEPT;
		} else {
			return FilterReply.NEUTRAL;
		}
	}

}
