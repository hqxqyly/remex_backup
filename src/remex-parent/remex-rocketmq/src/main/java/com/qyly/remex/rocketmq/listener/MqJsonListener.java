package com.qyly.remex.rocketmq.listener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.alibaba.fastjson.JSONObject;
import com.qyly.remex.utils.StringUtils;

/**
 * mq消费者监听器基础类，json格式
 * 
 * @author Qiaoxin.Hong
 *
 * @param <T> 消息对象
 */
public abstract class MqJsonListener<T> extends MqBaseListener {
	
	/** 消息对象Class */
	protected Class<T> msgClass;
	
	@SuppressWarnings("unchecked")
	public MqJsonListener() {
		//生成各泛型的class
		Type genType = getClass().getGenericSuperclass();   
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments(); 
		msgClass = (Class<T>) params[0];
	}
	
	/**
     * 处理消息
     * @param obj
     * @return
     */
	protected abstract boolean handle(T obj, String msgId);

	/**
     * 处理消息
     * @param message
     * @return
     */
	@Override
	protected boolean handleMessage(String message, String msgId) {
		T obj = null;
		if (StringUtils.isNotBlank(message)) {
			obj = JSONObject.parseObject(message, msgClass);
		}
		
		return handle(obj, msgId);
	}
}
