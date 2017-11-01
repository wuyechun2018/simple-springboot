package com.wyc.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * 统一异常处理
 * @desc: datacenter
 * @author: wyc
 * @createTime: 2017年4月21日 上午10:55:37
 * @history:
 * @version: v1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "error";
	private Logger logger =  LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = Exception.class)
	public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception ex) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String exMsg="";
		if (ex instanceof MaxUploadSizeExceededException) {
			System.out.println(" -- File Size Exceeds --");
			exMsg="文件太大,请重新上传！";
		}else if(ex.getClass().equals(DataAccessException.class)){
			exMsg="数据库操作失败";
		}else if(ex.getClass().equals(NullPointerException.class)) {
			exMsg = "空指针异常";
		} else if(ex.getClass().equals(IOException.class)) {
			exMsg = "IO异常";
		} else if(ex.getClass().equals(ClassNotFoundException.class)) {
			exMsg = "指定的类不存在";
		} else if (ex.getClass().equals(ArithmeticException.class)) {
			exMsg = "数学运算异常";
		}else if (ex.getClass().equals(IllegalArgumentException.class)) {
			exMsg = "方法的参数错误";
		}else if (ex.getClass().equals(ClassCastException.class)) {
			exMsg = "类型强制转换错误";
		}else if (ex.getClass().equals(SecurityException.class)) {
			exMsg = "违背安全原则异常";
		}else if (ex.getClass().equals(SQLException.class)) {
			exMsg = "SQL异常";
		}else if (ex.getClass().equals(NoSuchMethodError.class)) {
			exMsg = "方法末找到异常";
		}else if (ex.getClass().equals(InternalError.class)) {
			exMsg = "程序内部错误，操作失败";
		}else{
			exMsg=ex.getMessage();
		}
		mav.addObject("exception", ex);
		mav.addObject("exMsg", exMsg);
		mav.addObject("url", req.getRequestURL());
		
		logger.error("出错啦！路径：{},详情:{}",req.getRequestURI(),exMsg);
		
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}

}
