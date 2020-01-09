package com.qyly.remex.http.client;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.qyly.remex.exception.RemexException;
import com.qyly.remex.utils.Assert;

/**
 * http请求的结果集
 * 
 * @author Qiaoxin.Hong
 *
 */
public class HttpResult {

	/** 结果response */
	protected HttpResponse response;
	
	public HttpResult(HttpResponse response) {
		this.response = response;
	}
	
	/**
	 * 提取结果为字符串
	 * @return
	 */
	public String getStr() {
		Assert.notNull(response, "http result response is null");
		
		try {
			return EntityUtils.toString(response.getEntity(), Consts.UTF_8);
		} catch (Exception e) {
			throw new RemexException("http result get str error", e);
		}
	}
	
	/**
	 * 提取结果为某实体，由json转换
	 * @param clazz
	 * @return
	 */
	public <T> T getEntity(Class<T> clazz) {
		String json = getStr();
		return JSONObject.parseObject(json, clazz);
	}
	
	/**
	 * 提取结果为某实体，由json转换
	 * @param clazz
	 * @return
	 */
	public <T> T getEntity(TypeReference<T> type) {
		String json = getStr();
		return JSONObject.parseObject(json, type);
	}
	
	public HttpResponse getResponse() {
		return response;
	}
}
