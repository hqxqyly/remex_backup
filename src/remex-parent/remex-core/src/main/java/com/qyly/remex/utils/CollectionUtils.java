package com.qyly.remex.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 集合工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {

	/**
	 * 默认list，null => new ArrayList<>()
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<T> defaultList(List<T> list) {
		return list == null ? new ArrayList<T>() : list;
	}
	
	/**
	 * 默认set，null => new HashSet<>()
	 * 
	 * @param set
	 * @return
	 */
	public static <T> Set<T> defaultSet(Set<T> set) {
		return set == null ? new HashSet<>() : set;
	}
	
	/**
	 * 添加到集合
	 * @param coll
	 * @param val
	 */
	public static <T> void add(Collection<T> coll, T val) {
		if (coll == null) {
			return;
		}
		
		if (val != null) {
			coll.add(val);
		}
	}
	
	/**
	 * 批量添加到集合
	 * @param coll
	 * @param val
	 */
	public static <T> void add(Collection<T> coll, Collection<T> val) {
		if (coll == null) {
			return;
		}
		
		if (isNotEmpty(val)) {
			coll.addAll(val);
		}
	}
	
	/**
	 * 批量添加到集合
	 * @param coll
	 * @param val
	 */
	public static <T> void add(Collection<T> coll, T[] val) {
		if (coll == null) {
			return;
		}
		
		if (ObjectUtils.isNotEmpty(val)) {
			for (T t : val) {
				coll.add(t);
			}
		}
	}
	
	/**
	 * 集合转数组
	 * @param <T>
	 * @param clazz
	 * @param coll
	 * @return
	 */
	public static <T> T[] toArr(Class<T> clazz, Collection<T> coll) {
		if (coll == null) {
			return null;
		}
		
		@SuppressWarnings("unchecked")
		T[] arr = (T[]) Array.newInstance(clazz, coll.size());
		coll.toArray(arr);
		
		return arr;
	}
	
	/**
	 * 字符串集合转数组
	 * @param <T>
	 * @param clazz
	 * @param coll
	 * @return
	 */
	public static String[] toArrString(Collection<String> coll) {
		return toArr(String.class, coll);
	}
}
