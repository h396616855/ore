package com.github.ore.framework.web.ui;

public enum ColumnsStyleEnum {

	// 大驼峰式
	UPPER_CAMELCASE("UpperCamelCase"),

	// 小驼峰式
	LOWER_CAMELCASE("lowerCamelCase"),

	// 下划短横线隔开式
	UNDERLINE("underline");

	private String value;

	private ColumnsStyleEnum(String value) {
		this.value = value;
	}

	public String value() {
		return value;
	}
}
