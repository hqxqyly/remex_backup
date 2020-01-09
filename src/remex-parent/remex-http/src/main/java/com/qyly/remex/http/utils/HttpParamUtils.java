package com.qyly.remex.http.utils;

import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.qyly.remex.utils.Assist;

/**
 * http参数工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class HttpParamUtils {

	/**
	 * 创建NameValuePair
	 * @param key
	 * @param val
	 * @return
	 */
	public static NameValuePair createNameValuePair(String key, Object val) {
		Assist.notBlank(key, "createNameValuePair key cannot be blank");
		return new BasicNameValuePair(key, Assist.toString(val));
	}
	
	/**
	 * 创建NameValuePair列表
	 * @param key
	 * @param val
	 * @return
	 */
	public static List<NameValuePair> createNameValuePairList(Map<String, Object> map) {
		return Assist.forEachToList(map, HttpParamUtils::createNameValuePair);
	}
}
