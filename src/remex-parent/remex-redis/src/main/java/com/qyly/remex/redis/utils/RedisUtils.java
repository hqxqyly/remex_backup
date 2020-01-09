package com.qyly.remex.redis.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;

import com.alibaba.fastjson.JSONObject;
import com.qyly.remex.utils.ApplicationContextUtils;
import com.qyly.remex.utils.Assist;

/**
 * redis工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RedisUtils {

	/**
	 * 取得redis模板类
	 * @return
	 */
	public static RedisTemplate<String, Object> getRedisTemplate() {
		ApplicationContext applicationContext = ApplicationContextUtils.getContext();
		Assist.notNull(applicationContext, "applicationContext cannot be null");
		
		@SuppressWarnings("unchecked")
		RedisTemplate<String, Object> redisTemplate = (RedisTemplate<String, Object>) applicationContext.getBean("redisTemplate");
		Assist.notNull(redisTemplate, "redisTemplate cannot be null");
		return redisTemplate;
	}
	
	/**
	 * 取得opsForValue
	 * @return
	 */
	public static ValueOperations<String, Object> getOpsForValue() {
		return getRedisTemplate().opsForValue();
	}
	
	/**
	 * 取得opsForSet
	 * @return
	 */
	public static SetOperations<String, Object> getOpsForSet() {
		return getRedisTemplate().opsForSet();
	}
	
	/**
	 * 取得opsForList
	 * @return
	 */
	public static ListOperations<String, Object> getOpsForList() {
		return getRedisTemplate().opsForList();
	}
	
	/**
	 * 取得key列表
	 * @param keyPrefix
	 * @return
	 */
	public static Set<String> getKeys(String keyPrefix) {
		if (Assist.isBlank(keyPrefix)) return null;
		return getRedisTemplate().keys(keyPrefix);
	}
	
	/**
	 * 是否存在key
	 * @param key
	 * @return true：存在；false：不存在；
	 */
	public static boolean existKey(String key) {
		if (Assist.isBlank(key)) return false;
		return Assist.isNotEmpty(getKeys(key));
	}
	
	/**
	 * 保存到value
	 * @param key
	 * @param value
	 */
	public static void setToValue(String key, Object value) {
		if (Assist.isBlank(key)) return;
		getOpsForValue().set(key, value);
	}
	
	/**
	 * 保存到value
	 * @param key
	 * @param value
	 * @param seconds 失效时间，单位：秒
	 */
	public static void setToValue(String key, Object value, long seconds) {
		if (Assist.isBlank(key)) return;

		if (seconds > 0) {
			getOpsForValue().set(key, value, seconds, TimeUnit.SECONDS);
		} else {
			getOpsForValue().set(key, value);
		}
	}
	
	/**
	 * 保存到value，json格式
	 * @param key
	 * @param value
	 */
	public static void setToValueJson(String key, Object value) {
		if (Assist.isBlank(key)) return;
		getOpsForValue().set(key, Assist.toJson(value));
	}
	
	/**
	 * 保存到value，json格式
	 * @param key
	 * @param value
	 * @param seconds 失效时间，单位：秒
	 */
	public static void setToValueJson(String key, Object value, long seconds) {
		if (Assist.isBlank(key)) return;

		String json = Assist.ifNotNullFn(value, JSONObject::toJSONString);

		if (seconds > 0) {
			getOpsForValue().set(key, json, seconds, TimeUnit.SECONDS);
		} else {
			getOpsForValue().set(key, json);
		}
	}
	
	/**
	 * 从value取得数据
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getToValue(String key) {
		if (Assist.isBlank(key)) return null;
		return (T) getOpsForValue().get(key);
	}
	
	/**
	 * 从value取得数据，string格式
	 * @param key
	 * @return
	 */
	public static String getToValueString(String key) {
		if (Assist.isBlank(key)) return null;
		return (String) getOpsForValue().get(key);
	}
	
	/**
	 * 从value取得数据，Long格式
	 * @param key
	 * @return
	 */
	public static Integer getToValueInt(String key) {
		if (Assist.isBlank(key)) return null;
		return Assist.toInteger(getOpsForValue().get(key));
	}

	/**
	 * 从value取得数据，Long格式
	 * @param key
	 * @return
	 */
	public static Long getToValueLong(String key) {
		if (Assist.isBlank(key)) return null;
		return Assist.toLong(getOpsForValue().get(key));
	}

	/**
	 * 从value取得数据，json格式
	 * @param key
	 * @return
	 */
	public static <T> T getToValueJson(String key, Class<T> clazz) {
		if (Assist.isBlank(key)) return null;
		return JSONObject.parseObject(getToValueString(key), clazz);
	}
	
	/**
	 * 从value取得一批数据，json格式
	 * @param key
	 * @return
	 */
	public static <T> List<T> getToValueBatchJson(String keyPrefix, Class<T> clazz) {
		if (Assist.isBlank(keyPrefix)) return null;
		
		List<T> list = new ArrayList<>();
		Set<String> keys = getKeys(keyPrefix);
		
		Assist.forEach(keys, key -> {
			Assist.ifNotNull(getToValueJson(key, clazz), list::add);
		});
		
		return list;
	}
	
	/**
	 * 自增
	 * @param key
	 */
	public static void increment(String key) {
		if (Assist.isBlank(key)) return;
		getOpsForValue().increment(key, 1);
	}
	
	/**
	 * 自增
	 * @param key
	 */
	public static void increment(String key, long seconds) {
		if (Assist.isBlank(key)) return;
		
		if (!existKey(key)) {
			setToValue(key, 0, seconds);
		}
		getOpsForValue().increment(key, 1);
	}
	
	/**
	 * 自减
	 * @param key
	 */
	public static void decrement(String key) {
		if (Assist.isBlank(key)) return;
		getOpsForValue().increment(key, -1);
	}
	
	/**
	 * 自减
	 * @param key
	 */
	public static void decrement(String key, long seconds) {
		if (Assist.isBlank(key)) return;
		
		if (!existKey(key)) {
			setToValue(key, 0, seconds);
		}
		getOpsForValue().increment(key, -1);
	}
	
	/**
	 * 保存到set
	 * @param key
	 * @param value
	 */
	public static void setToSet(String key, Object value) {
		if (Assist.isBlank(key)) return;

		getOpsForSet().add(key, value);
	}
	
	/**
	 * 保存到set
	 * @param key
	 * @param vals
	 */
	public static void setToSet(String key, Collection<?> coll) {
		if (Assist.isBlank(key)) return;
		
		SetOperations<String, Object> op = getOpsForSet();
		Assist.forEach(coll, value -> op.add(key, value));
	}
	
	/**
	 * 保存到set
	 * @param key
	 * @param vals
	 */
	public static void setToSetJson(String key, Collection<?> coll) {
		if (Assist.isBlank(key)) return;
		
		SetOperations<String, Object> op = getOpsForSet();
		Assist.forEach(coll, value -> op.add(key, JSONObject.toJSONString(value)));
	}

	/**
	 * 从set取得数据，String格式
	 * @param key
	 * @return
	 */
	public static Set<String> getToSet(String key) {
		if (Assist.isBlank(key)) return null;
		Set<Object> set = getOpsForSet().members(key);
		return Assist.forEachToSet(set, Object::toString);
	}

	/**
	 * 从set删除数据
	 * @param key
	 * @param value
	 */
	public static void deleteToSet(String key, Object... value){
		if (Assist.isNotBlank(key) || value != null) {
			SetOperations<String, Object> op = getOpsForSet();
			op.remove(key, value);
		}
	}


	/**
	 * 从set取得数据，json格式
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> Set<T> getToSetJson(String key, Class<T> clazz) {
		if (Assist.isBlank(key)) return null;
		Set<Object> set = getOpsForSet().members(key);
		return Assist.forEachToSet(set, obj -> JSONObject.parseObject(obj.toString(), clazz));
	}
	
	/**
	 * 保存到list
	 * @param key
	 * @param value
	 */
	public static void setToList(String key, Object value) {
		if (Assist.isBlank(key)) return;
		getOpsForList().rightPush(key, value);
	}
	
	/**
	 * 保存到list
	 * @param key
	 * @param vals
	 */
	public static void setToList(String key, Collection<?> coll) {
		if (Assist.isBlank(key)) return;
		
		ListOperations<String, Object> op = getOpsForList();
		Assist.forEach(coll, value -> op.rightPush(key, value));
	}
	
	/**
	 * 保存到list
	 * @param key
	 * @param vals
	 */
	public static void setToListJson(String key, Collection<?> coll) {
		if (Assist.isBlank(key)) return;
		
		ListOperations<String, Object> op = getOpsForList();
		Assist.forEach(coll, value -> op.rightPush(key, JSONObject.toJSONString(value)));
	}
	
	/**
	 * 从list取得数据，json格式
	 * @param <T>
	 * @param key
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> getToListJson(String key, Class<T> clazz) {
		if (Assist.isBlank(key)) return null;
		
		ListOperations<String, Object> op = getOpsForList();
		List<Object> list = op.range(key, 0, -1);
		
		return Assist.forEachToList(list, obj -> JSONObject.parseObject(obj.toString(), clazz));
	}

	/**
	 * 删除数据
	 * @param key String
	 */
	public static void delKey(String key){
		if (Assist.isBlank(key)) return;
		
		getRedisTemplate().delete(key);
	}
	
	/**
	 * 批量删除数据
	 * @param key String
	 */
	public static void delKey(Set<String> keys){
		if (Assist.isEmpty(keys)) return;
		
		getRedisTemplate().delete(keys);
	}
	
	/**
	 * 根据前缀批量删除key
	 * @param key
	 */
	public static void delKeyByPrefix(String keyPrefix){
		Set<String> keys = getKeys(keyPrefix);
		getRedisTemplate().delete(keys);
	}


}
