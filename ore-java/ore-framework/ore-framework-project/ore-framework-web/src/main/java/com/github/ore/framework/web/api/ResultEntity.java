package com.github.ore.framework.web.api;

import java.util.Date;

public class ResultEntity<T> {
	// [系统级别]请求接口的返回码成功或者失败
	private String code = ResultMessage.splitCode(ResultMessage.SYSTEM_0);

	// [业务级别]按照业务需求定义code
	// private String errorCode = ResultMessage.splitCode(ResultMessage.BIZ_10000);
	private String errorCode = "";

	// 需要传递的信息，例如错误信息
	private String msg = ResultMessage.splitMsg(ResultMessage.SYSTEM_0);

	// private String time = DateFormat.getDateTimeInstance().format(new Date());
	// 返回访问时间单位为秒
	private String time = String.valueOf(new Date().getTime() / 1000);

	// 需要传递的数据
	private T data;

	public ResultEntity() {
		super();
	}

	public ResultEntity(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public ResultEntity(T data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

    /**
     * 得到属性字符串
     * @return String 属性字符串
     */
    public String toString()
    {
        String line = System.getProperty("line.separator");
        StringBuffer sb = new StringBuffer("{");
        sb.append(line);
        sb.append("code=").append((this.code)).append(line);
        sb.append("errorCode=").append((this.errorCode)).append(line);
        sb.append("msg=").append((this.msg)).append(line);
        sb.append("data=").append((this.data)).append(line);
        sb.append("time=").append((this.time)).append(line);
        sb.append("}");
        return sb.toString();
    }

}