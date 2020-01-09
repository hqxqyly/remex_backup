package com.qyly.remex.http.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.qyly.remex.exception.RemexException;
import com.qyly.remex.http.constant.HttpContentType;
import com.qyly.remex.http.constant.HttpRequestType;
import com.qyly.remex.http.utils.HttpParamUtils;
import com.qyly.remex.utils.Assist;
//import com.qyly.remex.utils.CollectionUtils;
//import com.qyly.remex.utils.ObjectUtils;
//import com.qyly.remex.utils.StringUtils;

/**
 * http client
 * 
 * @author Qiaoxin.Hong
 *
 */
public class HttpClient {
	
	/** 请求方式及相关参数 */
	protected HttpRequestBase httpRequest;
	
	/** 数据格式 */
	protected String contentType = HttpContentType.FORM;

	/** 请求URL */
	protected String url;
	
	/** 请求类型 */
	protected HttpRequestType requestType = HttpRequestType.POST;
	
	/** json请求参数 */
	protected String paramJson;
	
	/** 键值请求参数 */
	protected List<NameValuePair> paramNameValueList = new ArrayList<>();
	
	/** 连接超时时间 */
	protected int connectTimeout = 1000;
	
	/** 读取超时时间 */
	protected int socketTimeout = 60000;
	
	/** header参数列表 */
	protected List<Header> headerList = new ArrayList<>();
	
	
	public HttpClient() {
		
	}
	
	public HttpClient(String url) {
		super();
		setUrl(url);
	}
	
	public HttpClient(String url, HttpRequestBase httpRequest) {
		super();
		setUrl(url);
		setHttpRequest(httpRequest);
	}

	public HttpClient(String url, List<NameValuePair> paramNameValueList) {
		super();
		setUrl(url);
		setParamNameValueList(paramNameValueList);
	}

	public HttpClient(String url, String paramJson) {
		super();
		setUrl(url);
		setParamJson(paramJson);
	}
	
	public HttpClient(String url, Object paramJsonObj) {
		super();
		setUrl(url);
		setParamJson(paramJsonObj);
	}

	/**
	 * 以get及设置的参数发起http请求
	 * @return
	 */
	public HttpResult get() {
		setRequestType(HttpRequestType.GET);
		return call();
	}
	
	/**
	 * 以post及设置的参数发起http请求
	 * @return
	 */
	public HttpResult post() {
		setRequestType(HttpRequestType.POST);
		return call();
	}
	
	/**
	 * 以设置的请求方式及参数发起http请求
	 */
	public HttpResult call() {
		Assist.notBlank(url, "url cannot be blank");

		if (httpRequest == null) {
			httpRequest = Assist.ifTrue(HttpRequestType.POST.equals(requestType), this::createPost, this::createGet);
		}
		
		httpRequest.addHeader("Content-type", contentType);
		
		//初始化请求配置
		initRequestConfig();
		
		//填充header
		Assist.forEach(headerList, httpRequest::addHeader);
		
		//请求发送
		return callReal();
	}
	
	/**
	 * http请求，最后发送
	 * @return
	 */
	protected HttpResult callReal() {
		Assist.notBlank(url, "url cannot be blank");
		Assist.notNull(httpRequest, "httpRequest cannot be blank");
		
		try {
			// 创建默认的httpClient实例
			CloseableHttpClient httpClient = HttpClients.createDefault();
			
			HttpResponse response = httpClient.execute(httpRequest);
			return new HttpResult(response);
		} catch (Exception e) {
			throw new RemexException("http call real error！", e);
		}
	}
	
	/**
	 * 初始化请求配置
	 * @param a
	 */
	protected void initRequestConfig() {
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(connectTimeout)
				.setSocketTimeout(socketTimeout)
				.build();
		httpRequest.setConfig(requestConfig);
	}
	
	/**
	 * 创建HttpPost
	 * @return
	 */
	protected HttpPost createPost() {
		HttpPost httpPost = new HttpPost(url);
		//json请求参数
		Assist.ifNotBlank(paramJson, paramJson -> httpPost.setEntity(new StringEntity(paramJson, Consts.UTF_8)));

		//键值请求参数
		Assist.ifNotEmpty(paramNameValueList, paramNameValueList -> httpPost.setEntity(new UrlEncodedFormEntity(
				paramNameValueList, Consts.UTF_8)));

		return httpPost;
	}
	
	/**
	 * 创建HttpGet
	 * @return
	 */
	protected HttpGet createGet() {
		try {
			URIBuilder uriBuilder = new URIBuilder(url);
			
			//键值请求参数
			Assist.ifNotEmpty(paramNameValueList, uriBuilder::setParameters);
			
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			
			return httpGet;
		} catch (Exception e) {
			throw new RemexException("create HttpGet error", e);
		}
	}
	
	
	/**
	 * 设置参数
	 * @return
	 */
	public HttpClient addParam(String key, String val) {
		NameValuePair nameValuePair = HttpParamUtils.createNameValuePair(key, val);
		paramNameValueList.add(nameValuePair);
		return this;
	}
	
	/**
	 * 设置参数
	 * @param params
	 * @return
	 */
	public HttpClient setParams(Map<String, Object> params) {
		setContentType(HttpContentType.FORM);
		List<NameValuePair> list = HttpParamUtils.createNameValuePairList(params);
		Assist.forEach(list, paramNameValueList::add);
		return this;
	}
	
	/**
	 * 设置json请求参数
	 * @param obj
	 * @return
	 */
	public HttpClient setParamJson(Object paramJsonObj) {
		setContentType(HttpContentType.JSON);
		
		if (paramJsonObj != null) {
			if (paramJsonObj instanceof String) {
				paramJson = (String) paramJsonObj;
			} else {
				paramJson = JSONObject.toJSONString(paramJsonObj);
			}
		}
		return this;
	}
	
	/**
	 * 设置header
	 * @param header
	 * @return
	 */
	public HttpClient addHeader(Header header) {
		headerList.add(header);
		return this;
	}
	
	/**
	 * 设置header
	 * @param header
	 * @return
	 */
	public HttpClient addHeader(String name, String value) {
		Assist.notBlank(name, "header name is blank");
		Header header = new BasicHeader(name, value);
		headerList.add(header);
		return this;
	}
	
	/**
	 * 设置header
	 * @param headerMap
	 * @return
	 */
	public HttpClient setHeader(Map<String, String> headerMap) {
		Assist.forEach(headerMap, this::addHeader);
		return this;
	}
	
	
	
	/**
	 * post请求
	 * @param url
	 * @return
	 */
	public static HttpResult post(String url) {
		return new HttpClient(url).post();
	}
	
	/**
	 * post请求，json格式
	 * @param url
	 * @param json
	 * @return
	 */
	public static HttpResult post(String url, String json) {
		return new HttpClient(url, json).post();
	}
	
	/**
	 * post请求，json格式
	 * @param url
	 * @param obj
	 * @return
	 */
	public static HttpResult post(String url, Object obj) {
		return post(url, obj, null);
	}
	
	/**
	 * post请求，json格式
	 * @param url
	 * @param obj
	 * @return
	 */
	public static HttpResult post(String url, Object obj, Map<String, String> headerMap) {
		return new HttpClient(url, obj).setHeader(headerMap).post();
	}
	
	/**
	 * post请求，json格式
	 * @param url
	 * @param obj
	 * @return
	 */
	public static void postJson(String url, Object obj) {
		post(url, obj);
	}
	
	/**
	 * post请求，json格式，并返回对象
	 * @param url
	 * @param obj
	 * @return
	 */
	public static <T> T postJson(String url, Object obj, Class<T> clazz) {
		return postJson(url, obj, clazz, null);
	}
	
	/**
	 * post请求，json格式，并返回对象
	 * @param url
	 * @param obj
	 * @return
	 */
	public static <T> T postJson(String url, Object obj, Class<T> clazz, Map<String, String> headerMap) {
		HttpResult httpResult = post(url, obj, headerMap);
		Assist.notNull(httpResult, "httpResult cannot be null");
		
		return httpResult.getEntity(clazz);
	}
	
	/**
	 * post请求，json格式，并返回对象
	 * @param url
	 * @param obj
	 * @return
	 */
	public static <T> T postJson(String url, Object obj, TypeReference<T> type) {
		HttpResult httpResult = post(url, obj);
		Assist.notNull(httpResult, "httpResult cannot be null");
		
		return httpResult.getEntity(type);
	}
	
	/**
	 * post请求，json格式，并返回对象
	 * @param url
	 * @param obj
	 * @return
	 */
	public static <T> T postJson(String url, Object obj, TypeReference<T> type, Map<String, String> headerMap) {
		HttpResult httpResult = post(url, obj, headerMap);
		Assist.notNull(httpResult, "httpResult cannot be null");
		
		return httpResult.getEntity(type);
	}
	

	public String getUrl() {
		return url;
	}

	public HttpClient setUrl(String url) {
		this.url = url;
		return this;
	}

	public HttpRequestType getRequestType() {
		return requestType;
	}

	public HttpClient setRequestType(HttpRequestType requestType) {
		this.requestType = requestType;
		return this;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public HttpClient setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
		return this;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public HttpClient setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
		return this;
	}

	public String getContentType() {
		return contentType;
	}

	public HttpClient setContentType(String contentType) {
		this.contentType = contentType;
		return this;
	}

	public HttpRequestBase getHttpRequest() {
		return httpRequest;
	}

	public HttpClient setHttpRequest(HttpRequestBase httpRequest) {
		this.httpRequest = httpRequest;
		return this;
	}
	
	public void setHeaderList(List<Header> headerList) {
		this.headerList = headerList;
	}
	
	public List<Header> getHeaderList() {
		return headerList;
	}
	
	public String getParamJson() {
		return paramJson;
	}

	public HttpClient setParamJson(String paramJson) {
		setContentType(HttpContentType.JSON);
		this.paramJson = paramJson;
		return this;
	}
	
	public HttpClient setParamNameValueList(List<NameValuePair> paramNameValueList) {
		setContentType(HttpContentType.FORM);
		this.paramNameValueList = paramNameValueList;
		return this;
	}

	public List<NameValuePair> getParamNameValueList() {
		return paramNameValueList;
	}
}
