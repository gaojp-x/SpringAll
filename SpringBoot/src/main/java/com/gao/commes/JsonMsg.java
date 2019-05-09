package com.gao.commes;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * @author 高家鹏 [1772525701@qq.com]
 *
 */
public class JsonMsg {

	private int code; // 返回码 非0即失败
	private String msg; // 消息提示
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

	public JsonMsg() {
	}

	public JsonMsg(int code, String msg, Map<String, Object> data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}
	/**
	 * @return 解析成功
	 */
	public static String success() {
		return success(null);
	}
	/**
	 * 解析成功
	 * @param data 返回的数据
	 * @return json串
	 */
	public static String success(Map<String, Object> data) {
		return failed(0, "解析成功", data);
	}
	/**
	 * @return 解析失败
	 */
	public static String failed() {
		return failed("解析失败");
	}
	/**
	 * 解析失败
	 * @param msg 失败信息
	 * @return json串
	 */
	public static String failed(String msg) {
		return failed(-1, msg,null);
	}
	/**
	 * 返回自定义消息
	 * @param code 返回码
	 * @param msg 返回消息
	 * @param map 数据
	 * @return json串
	 */
	public static String failed(int code, String msg,Map<String,Object> map) {
		String result = JSON.toJSONString(new JsonMsg(code, msg, map),SerializerFeature.WriteMapNullValue);
		System.out.println("返回消息:"+result);
		return result;
	}
	public static void main(String[] args) {
		String failed = failed(0, "00", null);
		System.out.println(failed);
	}
}
