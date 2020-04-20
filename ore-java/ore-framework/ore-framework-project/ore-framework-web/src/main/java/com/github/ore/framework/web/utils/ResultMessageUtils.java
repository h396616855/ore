package com.github.ore.framework.web.utils;

import com.github.ore.framework.web.api.ResultMessage;

/**
 * ResultMessage 消息截取工具
 *
 */
public class ResultMessageUtils {

	public static String splitCode(String message) {
		String[] str = message.split("\\^");
		return str[0];
	}

	public static String splitMsg(String message) {
		String[] str = message.split("\\^");
		return str[1];
	}

	public static void main(String[] args) {
		System.out.println(ResultMessageUtils.splitCode(ResultMessage.BIZ_20000));
		System.out.println(ResultMessageUtils.splitMsg(ResultMessage.BIZ_20000));
	}
}
