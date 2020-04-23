package com.github.ore.boot.autoconfigure.context.custom;

import java.net.ConnectException;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.github.ore.framework.web.api.ResultEntity;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 统一处理异常,全局异常处理，只处理[系统级别]异常
 *
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	private final static String leftBrackets = "[";

	private final static String rightBrackets = "]";

	private ResultEntity<String> extractedValidParams(ResultEntity<String> result, List<ObjectError> objectErrors) {
		StringBuilder msgBuilder = new StringBuilder();
		for (ObjectError objectError : objectErrors) {
			msgBuilder.append(objectError.getDefaultMessage()).append(",");
		}
		String errorMessage = msgBuilder.toString();
		if (errorMessage.length() > 1) {
			errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
			if (errorMessage.contains(leftBrackets) && errorMessage.contains(rightBrackets)) {
				int start = errorMessage.indexOf("[");
				int end = errorMessage.indexOf("]");
				result.setData(errorMessage.substring(start + 1, end));
			}
		}

		result.setMsg(errorMessage);
		return result;
	}

	/**
	 * 请求参数类型错误异常的捕获
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = { BindException.class })
	@ResponseBody
	public ResultEntity<String> badRequest(BindException e) {
		log.error("occurs error when execute method ,message {}", e.getMessage());
		ExceptionUtils.printRootCauseStackTrace(e);
		ResultEntity<String> result = new ResultEntity<String>();
		result.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
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
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResultEntity<String> badRequestNotFound(HttpServletRequest request, HttpServletResponse response, final Exception e) {
		log.error("occurs error when execute method ,message {}", e.getMessage());
		ExceptionUtils.printRootCauseStackTrace(e);
		ResultEntity<String> result = new ResultEntity<String>();
		result.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
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
	@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
	public ResultEntity<String> connect(Exception e) {
		log.error("occurs error when execute method ,message {}", e.getMessage());
		ExceptionUtils.printRootCauseStackTrace(e);
		ResultEntity<String> result = new ResultEntity<String>();
		result.setCode(String.valueOf(HttpStatus.GATEWAY_TIMEOUT.value()));
		result.setMsg(e.getMessage());
		return result;
	}

	/**
	 * 用来处理bean validation异常
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResultEntity<String> validException(MethodArgumentNotValidException e) {
		log.error("occurs error when execute method ,message {}", e.getMessage());
		ResultEntity<String> result = new ResultEntity<String>();
		result.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
		if (!CollectionUtils.isEmpty(objectErrors)) {
			return extractedValidParams(result, objectErrors);
		}
		result.setMsg(e.getMessage());
		return result;
	}

	/**
	 * 用来处理bean validation异常
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseBody
	public ResultEntity<String> validException(ConstraintViolationException e) {
		log.error("occurs error when execute method ,message {}", e.getMessage());
		ResultEntity<String> result = new ResultEntity<String>();
		result.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
		if (!CollectionUtils.isEmpty(constraintViolations)) {
			StringBuilder msgBuilder = new StringBuilder();
			for (ConstraintViolation<?> constraintViolation : constraintViolations) {
				msgBuilder.append(constraintViolation.getMessage()).append(",");
			}
			String errorMessage = msgBuilder.toString();
			if (errorMessage.length() > 1) {
				errorMessage = errorMessage.substring(0, errorMessage.length() - 1);
				if (errorMessage.contains(leftBrackets) && errorMessage.contains(rightBrackets)) {
					int start = errorMessage.indexOf("[");
					int end = errorMessage.indexOf("]");
					result.setData(errorMessage.substring(start + 1, end));
				}
			}
			result.setMsg(errorMessage);
			return result;
		}
		result.setMsg(e.getMessage());
		return result;
	}

	@ExceptionHandler(org.springframework.validation.BindException.class)
	@ResponseBody
	public ResultEntity<String> validException(org.springframework.validation.BindException e) {
		log.error("occurs error when execute method ,message {}", e.getMessage());
		ResultEntity<String> result = new ResultEntity<String>();
		result.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
		List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();
		if (!CollectionUtils.isEmpty(objectErrors)) {
			return extractedValidParams(result, objectErrors);
		}

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
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResultEntity<String> globalException(Exception e) {
		log.error("occurs error when execute method ,message {}", e.getMessage());
		ExceptionUtils.printRootCauseStackTrace(e);
		ResultEntity<String> result = new ResultEntity<String>();
		result.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
		result.setMsg(e.getMessage());
		return result;
	}
}
