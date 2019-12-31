package com.github.ore.boot.autoconfigure.context;

import java.net.ConnectException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.ore.framework.web.api.ResultEntity;
import com.github.ore.framework.web.api.ResultMessage;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 统一处理异常,全局异常处理
 * 
 * @author huangzz
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	/**
	 * 请求参数类型错误异常的捕获
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { BindException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResultEntity<String> badRequest(BindException e) {
		log.error("occurs error when execute method ,message {}", e.getMessage());
		ExceptionUtils.printRootCauseStackTrace(e);
		ResultEntity<String> result = new ResultEntity<String>();
		result.setErrorCode(ResultMessage.splitCode(ResultMessage.HTTP_ERROR_400));
		result.setMsg(e.getMessage());
		return result;
	}

	/**
	 * 404错误异常的捕获
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { NoHandlerFoundException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResultEntity<String> badRequestNotFound(HttpServletRequest request, HttpServletResponse response, final Exception e) {
		log.error("occurs error when execute method ,message {}", e.getMessage());
		ExceptionUtils.printRootCauseStackTrace(e);
		ResultEntity<String> result = new ResultEntity<String>();
		result.setErrorCode(ResultMessage.splitCode(ResultMessage.HTTP_ERROR_404));
		result.setMsg(e.getMessage());
		return result;
	}

	/**
	 * 网络连接失败
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { ConnectException.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.GATEWAY_TIMEOUT)
	public ResultEntity<String> connect(Exception e) {
		log.error("occurs error when execute method ,message {}", e.getMessage());
		ExceptionUtils.printRootCauseStackTrace(e);
		ResultEntity<String> result = new ResultEntity<String>();
		result.setErrorCode(ResultMessage.splitCode(ResultMessage.HTTP_ERROR_504));
		result.setMsg(e.getMessage());
		return result;
	}

	/**
	 * 其他全局异常在此捕获
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { Throwable.class })
	@ResponseBody
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultEntity<String> notAllowed(Exception e) {
		log.error("occurs error when execute method ,message {}", e.getMessage());
		ExceptionUtils.printRootCauseStackTrace(e);
		ResultEntity<String> result = new ResultEntity<String>();
		result.setErrorCode(ResultMessage.splitCode(ResultMessage.HTTP_ERROR_500));
		result.setMsg(e.getMessage());
		return result;
	}
}
