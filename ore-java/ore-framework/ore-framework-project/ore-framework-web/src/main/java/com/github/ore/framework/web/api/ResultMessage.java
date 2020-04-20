package com.github.ore.framework.web.api;

/**
 * 约定： 返回码和文字描述格式：n_文字描述，使用的时候是通过按照位数截取。 ResultCode.java 这个类就不需要要了
 * 定义为接口，方便其他工程继承，添加自己的返回码和返回信息
 *
 */
public abstract class ResultMessage {

	/**
	 * 预定义返回码
	 */
	public static final String SYS_0 = "0^请求成功";
	public static final String SYS_1 = "1^请求失败";
	public static final String SYS_2 = "2^系统繁忙，请稍后再试";
	public static final String SYS_3 = "3^网络连接失败";
	public static final String SYS_4 = "4^请求参数类型错误异常的捕获";
	public static final String SYS_5 = "5^系统异常";
	public static final String SYS_6 = "6^系统繁忙，此时请开发者稍候再试";

	/**
	 * 例子：自定义应用异常码：APP_1XXXX
	 */
	public static final String APP_10000 = "10000^应用正常";

	/**
	 * 例子：自定义业务返回码：BIZ_2XXXX,BIZ_3XXXX,BIZ_4XXXX,BIZ_5XXXX 等
	 */
	public static final String BIZ_20000 = "20000^业务正常";

}
