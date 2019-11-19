package com.qyly.remex.utils;

import java.io.Closeable;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对象工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class ObjectUtils {
	private static Logger logger = LoggerFactory.getLogger(ObjectUtils.class);
	
	/**
	 * 是否为空
	 * @param val
	 * @return
	 */
	public static <T> boolean isEmpty(Collection<?> coll) {
		return coll == null || coll.size() == 0;
	}
	
	/**
	 * 是否不为空
	 * @param val
	 * @return
	 */
	public static <T> boolean isNotEmpty(Collection<?> coll) {
		return !isEmpty(coll);
	}
	
	/**
	 * 是否为空
	 * @param val
	 * @return
	 */
	public static <T> boolean isBlank(String val) {
		return StringUtils.isBlank(val);
	}
	
	/**
	 * 是否不为空
	 * @param val
	 * @return
	 */
	public static <T> boolean isNotBlank(String val) {
		return !isBlank(val);
	}

	/**
	 * 是否为空
	 * @param val
	 * @return
	 */
	public static <T> boolean isEmpty(T[] val) {
		return val == null || val.length == 0;
	}
	
	/**
	 * 是否不为空
	 * @param val
	 * @return
	 */
	public static <T> boolean isNotEmpty(T[] val) {
		return !isEmpty(val);
	}
	
	/**
	 * 是否为空
	 * @param val
	 * @return
	 */
	public static boolean isEmpty(Map<?, ?> val) {
		return val == null || val.size() == 0;
	}
	
	/**
	 * 是否不为空
	 * @param val
	 * @return
	 */
	public static boolean isNotEmpty(Map<?, ?> val) {
		return !isEmpty(val);
	}
	
	/**
	 * 转Long
	 * @param val
	 * @return
	 */
	public static Long toLong(Object val) {
		if (val == null) {
			return null;
		}
		if (val instanceof Long) {
			return (Long) val;
		}
		return Long.valueOf(val.toString());
	}
	
	/**
	 * 默认字符串数组
	 * @param arr
	 * @return
	 */
	public static String[] defaultArrString(String[] arr) {
		return arr == null ? new String[] {} : arr; 
	}
	
	/**
	 * 资源释放
	 * @param sources
	 */
	public static void close(Closeable...sources) {
		for (Closeable source : sources) {
			if (source != null) {
				try {
					source.close();
				} catch (Exception e) {
					logger.error("source close error!", e);
				}
			}
		}
	}
}
