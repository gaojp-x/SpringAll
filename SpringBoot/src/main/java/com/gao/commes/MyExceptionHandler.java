package com.gao.commes;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理拦截器
 * 
 * @author 
 */
@CrossOrigin
@RestControllerAdvice
public class MyExceptionHandler {
	
	//MyExceptionHandler捕获异常：代码：%s 详细信息：%s
	private static final String logExceptionFormat = "Capture Exception By MyExceptionHandler: Code: %s Detail: %s";
    //获得日志记录对象  
	private static Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

	// 运行时异常
	@ExceptionHandler(RuntimeException.class)
	public String runtimeExceptionHandler(RuntimeException ex) {
		System.out.println("运行时异常");
		return exceptionFormat(1, ex);
	}

	@ResponseBody
	// 空指针异常
	@ExceptionHandler(NullPointerException.class)
	public String nullPointerExceptionHandler(NullPointerException ex) {
		System.out.println("空指针异常");
		return exceptionFormat(2, ex);
	}

	// 类型转换异常
	@ExceptionHandler(ClassCastException.class)
	public String classCastExceptionHandler(ClassCastException ex) {
		System.out.println("类型转换异常");
		return exceptionFormat(3, ex);
	}

	// IO异常
	@ExceptionHandler(IOException.class)
	public String iOExceptionHandler(IOException ex) {
		System.out.println("IO异常");
		return exceptionFormat(4, ex);
	}

	// 未知方法异常
	@ExceptionHandler(NoSuchMethodException.class)
	public String noSuchMethodExceptionHandler(NoSuchMethodException ex) {
		System.out.println("未知方法异常");
		return exceptionFormat(5, ex);
	}

	// 数组越界异常
	@ExceptionHandler(IndexOutOfBoundsException.class)
	public String indexOutOfBoundsExceptionHandler(IndexOutOfBoundsException ex) {
		System.out.println("数组越界异常");
		return exceptionFormat(6, ex);
	}

	// 400错误
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	public String requestNotReadable(HttpMessageNotReadableException ex) {
		System.out.println("400..requestNotReadable");
		return exceptionFormat(7, ex);
	}

	// 400错误
	@ExceptionHandler({ TypeMismatchException.class })
	public String requestTypeMismatch(TypeMismatchException ex) {
		System.out.println("400..TypeMismatchException");
		return exceptionFormat(8, ex);
	}

	// 400错误
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	public String requestMissingServletRequest(MissingServletRequestParameterException ex) {
		System.out.println("400..MissingServletRequest");
		return exceptionFormat(9, ex);
	}

	// 405错误
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public String request405(HttpRequestMethodNotSupportedException ex) {
		System.out.println("405错误");
		return exceptionFormat(10, ex);
	}

	// 406错误
	@ExceptionHandler({ HttpMediaTypeNotAcceptableException.class })
	public String request406(HttpMediaTypeNotAcceptableException ex) {
		System.out.println("406...");
		return exceptionFormat(11, ex);
	}

	// 500错误
	@ExceptionHandler({ ConversionNotSupportedException.class, HttpMessageNotWritableException.class })
	public String server500(RuntimeException ex) {
		System.out.println("500...");
		return exceptionFormat(12, ex);
	}

	// 栈溢出
	@ExceptionHandler({ StackOverflowError.class })
	public String requestStackOverflow(StackOverflowError ex) {
		System.out.println("栈溢出");
		return exceptionFormat(13, ex);
	}

	// 其他错误
	@ExceptionHandler({ Exception.class })
	public String exception(Exception ex) {
		System.out.println("其他错误");
		return exceptionFormat(14, ex);
	}

	// 自定义异常捕获
	@ExceptionHandler({ MyException.class })
	public String myException(MyException ex) {
		System.out.println("自定义异常捕获 ");
		return exceptionFormat(999, ex);
	}
	//记录异常信息,返回json
	private <T extends Throwable> String exceptionFormat(Integer code, T ex) {
		
		String exMsg = ex.toString();
		
		log.error(String.format(logExceptionFormat, code, exMsg),ex);
		/*
		 * // 记录error信息  
		 * logger.error("[info message]"); 
		 * // 记录info，还可以传入参数
		 * logger.info("[info message]{},{},{},{}", "abc", false, 123, new Slf4jTest());   
		 * // 记录deubg信息  
		 * logger.debug("[debug message]"); 
		 * // 记录trace信息 
		 *  logger.trace("[trace message]");
		 */ 
		return JsonMsg.failed(code, exMsg,null);
	}
}
