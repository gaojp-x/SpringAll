package com.example.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @author 高家鹏 [1772525701@qq.com]
 *
 */
public class JsonResult {

	private static final Logger log = LoggerFactory.getLogger(JsonResult.class);
	
	private int code; // 返回码
	private String msg; // 返回的消息提示
	private Map<String, Object> data; // 返回的数据

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public JsonResult() {
	}

	public JsonResult(int code, String msg, Map<String, Object> data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * @return 成功
	 */
	public static String success() {
		return success(0, "成功", null);
	}

	/**
	 * 成功
	 * 
	 * @param data 返回的数据
	 * @return json串
	 */
	public static String success(int code, String msg, Map<String, Object> data) {
		return Result(code, msg, data);
	}

	/**
	 * @return 错误
	 */
	public static String error() {
		return error(-1, "错误",null);
	}

	/**
	 * 错误
	 * 
	 * @param msg 错误信息
	 * @return json串
	 */
	public static String error(int code, String msg, Map<String, Object> data) {
		return Result(-1, msg, null);
	}

	/**
	 * 返回自定义消息
	 * 
	 * @param code 返回码
	 * @param msg  返回消息
	 * @param map  数据
	 * @return json串
	 */
	public static String Result(int code, String msg, Map<String, Object> map) {
		String result = JSON.toJSONString(new JsonResult(code, msg, map), SerializerFeature.WriteMapNullValue);
		// System.out.println("返回消息:"+result);
		log.info("com.example.utils.JsonResult返回  {}",result);
		return result;
	}

	/*public static void main(String[] args) {
		String failed = Result(0, "00", null);
		System.out.println(failed);
	}*/
}
