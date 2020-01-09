package com.github.ore.framework.web.api;

import java.util.Date;

import com.github.ore.framework.web.utils.ResultMessageUtils;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class ResultEntity<T> {

	/**
	 * 请求接口的返回码
	 */
	private String code = ResultMessageUtils.splitCode(ResultMessage.SYSTEM_0);

	/**
	 * 需要传递的信息，例如错误信息
	 */
	private String msg = ResultMessageUtils.splitMsg(ResultMessage.SYSTEM_0);

	/**
	 * 返回访问时间单位为毫秒
	 */
	private String time = String.valueOf(new Date().getTime());

	/**
	 * 需要传递的数据
	 */
	private T data;

	public ResultEntity(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}