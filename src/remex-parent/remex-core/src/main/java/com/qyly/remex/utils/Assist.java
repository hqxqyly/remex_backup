package com.qyly.remex.utils;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.qyly.remex.constant.BConst;
import com.qyly.remex.exception.RemexException;

/**
 * 辅助工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class Assist {
	protected final static Logger logger = LoggerFactory.getLogger(Assist.class);

	/**
	 * 如果值存在则执行consumer（接受一个输入参数，无返回的操作），否则不做任何事情
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <T> void consumer(T obj, Consumer<T> action) {
		if (obj != null)
			action.accept(obj);
	}
	
	/**
	 * 如果值存在则执行function（接受一个输入参数，返回一个结果），否则不做任何事情
	 * @param <T>
	 * @param <R>
	 * @param obj
	 * @param action
	 * @return
	 */
	public static <T, R> R function(T obj, Function<T, R> action) {
		if (obj != null)
			return action.apply(obj);
		return null;
	}
	
	/**
	 * 如果值存在则执行function（接受一个输入参数，返回一个结果），否则不做任何事情
	 * @param <T>
	 * @param <R>
	 * @param obj
	 * @param function
	 * @return
	 */
	public static <R> R supplier(Object obj, Supplier<R> action) {
		if (obj != null)
			return action.get();
		return null;
	}
	
	
	
	/**
	 * 如果值存在则循环执行consumer，否则不做任何事情
	 * @param <T>
	 * @param coll
	 * @param action
	 */
	public static <T> void forEach(Collection<T> coll, Consumer<T> action) {
		consumer(coll, o -> o.forEach(action));
	}
	
	/**
	 * 如果值存在则循环执行consumer，否则不做任何事情
	 * @param <T>
	 * @param arr
	 * @param action
	 */
	public static <T> void forEach(T[] arr, Consumer<T> action) {
		consumer(arr, o -> Arrays.stream(arr).forEach(action));
	}
	
	/**
	 * 如果值存在则循环执行BiConsumer，否则不做任何事情
	 * @param <T>
	 * @param arr
	 * @param action
	 */
	public static <K, V> void forEach(Map<K, V> map, BiConsumer<K, V> action) {
		consumer(map, o -> map.forEach(action));
	}
	
	/**
	 * 如果值存在则循环执行function转换为List，否则不做任何事情
	 * @param <T>
	 * @param <R>
	 * @param coll
	 * @param action
	 * @return
	 */
	public static <T, R> List<R> forEachToList(Collection<T> coll, Function<T, R> action) {
		return coll == null ? new ArrayList<>() : coll.stream().map(action).collect(Collectors.toList());
	}
	
	/**
	 * 如果值存在则循环执行function转换为List，否则不做任何事情
	 * @param <T>
	 * @param <R>
	 * @param coll
	 * @param action
	 * @return
	 */
	public static <T, R> List<R> forEachToList(T[] arr, Function<T, R> action) {
		return arr == null ? new ArrayList<>() : Arrays.stream(arr).map(action).collect(Collectors.toList());
	}
	
	/**
	 * 如果值存在则循环执行BiFunction转换为List，否则不做任何事情
	 * @param <T>
	 * @param <R>
	 * @param coll
	 * @param action
	 * @return
	 */
	public static <R, K, V> List<R> forEachToList(Map<K, V> map, BiFunction<K, V, R> action) {
		List<R> list = new ArrayList<>();
		forEach(map, (key, val) -> action.apply(key, val));
		return list;
	}
	
	/**
	 * 如果值存在则循环执行function转换为Set，否则不做任何事情
	 * @param <T>
	 * @param <R>
	 * @param coll
	 * @param action
	 * @return
	 */
	public static <T, R> Set<R> forEachToSet(Collection<T> coll, Function<T, R> action) {
		return coll == null ? new HashSet<>() : coll.stream().map(action).collect(Collectors.toSet());
	}
	
	/**
	 * 如果值存在则循环执行function转换为Set，否则不做任何事情
	 * @param <T>
	 * @param <R>
	 * @param coll
	 * @param action
	 * @return
	 */
	public static <T, R> Set<R> forEachToSet(T[] arr, Function<T, R> action) {
		return arr == null ? new HashSet<>() : Arrays.stream(arr).map(action).collect(Collectors.toSet());
	}
	
	/**
	 * 如果值存在则循环执行BiFunction转换为Set，否则不做任何事情
	 * @param <T>
	 * @param <R>
	 * @param coll
	 * @param action
	 * @return
	 */
	public static <R, K, V> Set<R> forEachToSet(Map<K, V> map, BiFunction<K, V, R> action) {
		Set<R> list = new HashSet<>();
		forEach(map, (key, val) -> action.apply(key, val));
		return list;
	}
	
	
	
	
	
	/**
	 * 如果不为null，则执行consumer，否则不做任何事情
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <T> void ifNotNull(T obj, Consumer<T> action) {
		if (obj != null)
			action.accept(obj);
	}
	
	/**
	 * 如果不为blank，则执行consumer，否则不做任何事情
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static void ifNotBlank(String obj, Consumer<String> action) {
		if (isNotBlank(obj))
			action.accept(obj);
	}
	
	/**
	 * 如果不为empty，则执行consumer，否则不做任何事情
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <T, C extends Collection<T>> void ifNotEmpty(C obj, Consumer<C> action) {
		if (isNotEmpty(obj))
			action.accept(obj);
	}
	
	/**
	 * 如果不为empty，则执行consumer，否则不做任何事情
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <K, V> void ifNotEmpty(Map<K, V> obj, Consumer<Map<K, V>> action) {
		if (isNotEmpty(obj))
			action.accept(obj);
	}
	
	/**
	 * 如果不为null，则执行function，否则不做任何事情
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <T, R> R ifNotNullFn(T obj, Function<T, R> action) {
		return obj == null ? null : action.apply(obj);
	}
	
	/**
	 * 如果不为blank，则执行function，否则不做任何事情
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <R> R ifNotBlankFn(String obj, Function<String, R> action) {
		return isBlank(obj) ? null : action.apply(obj);
	}
	
	/**
	 * 如果不为blank，则执行function，否则返回默认值
	 * @param obj
	 * @param action
	 */
	public static String ifNotBlankFnDefaultString(String obj, Function<String, String> action, String defaultStr) {
		return isBlank(obj) ? defaultStr : action.apply(obj);
	}
	
	/**
	 * 如果为true，则执行Supplier，否则不做任何事情
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <T> T ifTrue(boolean obj, Supplier<T> action) {
		return obj ? action.get() : null;
	}
	
	/**
	 * 如果为false，则执行Supplier，否则不做任何事情
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <T> T ifFalse(boolean obj, Supplier<T> action) {
		return !obj ? action.get() : null;
	}
	
	/**
	 * 如果为true，则执行Supplier1，否则执行Supplier2
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <T> T ifTrue(boolean obj, Supplier<T> actionOne, Supplier<T> actionTwo) {
		if (obj) 
			return actionOne.get();
		else
			return actionTwo.get();
	}
	
	/**
	 * 如果为false，则执行Supplier1，否则执行Supplier2
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <T> T ifFalse(boolean obj, Supplier<T> actionOne, Supplier<T> actionTwo) {
		if (!obj) 
			return actionOne.get();
		else
			return actionTwo.get();
	}
	
	/**
	 * 如果为equals为true，则执行Supplier1，否则执行Supplier2
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <T> T ifEqualsTrue(Object objOne, Object objTwo, Supplier<T> actionOne, Supplier<T> actionTwo) {
		if (objOne == null) {
			if (objTwo == null)
				return actionOne.get();
			else
				return actionTwo.get();
		}
		if (objTwo == null)
			return actionTwo.get();
			
		if (objOne.equals(objTwo)) 
			return actionOne.get();
		else
			return actionTwo.get();
	}
	
	
	
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
	 * <pre>
	 * 默认字符串
	 * "a" => "a"
	 * "  " => defaultStr
	 * null => defaultStr
	 * </pre>
	 * @param str
	 */
	public static String defaultString(String str, String defaultStr) {
		return isBlank(str) ? defaultStr : str;
	}
	
	/**
	 * 默认字符串
	 * @param val
	 * @return
	 */
	public static String defaultString(Object val) {
		return val == null ? BConst.EMPTY : val.toString();
	}
	
	/**
	 * <pre>
	 * 默认字符串
	 * "a" => "a"
	 * "  " => ""
	 * null => ""
	 * </pre>
	 * @param str
	 */
	public static String defaultBlank(String str) {
		return defaultString(str, BConst.EMPTY);
	}
	
	/**
	 * <pre>
	 * 默认字符串
	 * "a" => "a"
	 * "  " => ""
	 * null => ""
	 * </pre>
	 * @param str
	 */
	public static void defaultBlank(String str, Consumer<String> action) {
		action.accept(defaultBlank(str));
	}
	
	/**
	 * 默认值
	 * @param <T>
	 * @param val
	 * @param defaultVal
	 * @return
	 */
	public static <T> T defaultVal(T val, T defaultVal) {
		return val == null ? defaultVal : val;
	}
	
	/**
	 * 默认值
	 * @param <T>
	 * @param val
	 * @param defaultVal
	 * @return
	 */
	public static <T> void defaultVal(T val, T defaultVal, Consumer<T> action) {
		T finalVal = defaultVal(val, defaultVal);
		action.accept(finalVal);
	}
	
	/**
	 * 默认List，null => new ArrayList<>()
	 * 
	 * @param list
	 * @return
	 */
	public static <T> List<T> defaultList(List<T> list) {
		return list == null ? new ArrayList<T>() : list;
	}
	
	/**
	 * 默认Set，null => new HashSet<>()
	 * 
	 * @param list
	 * @return
	 */
	public static <T> Set<T> defaultSet(Set<T> list) {
		return list == null ? new HashSet<T>() : list;
	}
	
	
	
	/**
	 * 不能为null
	 * @param <T>
	 * @param obj
	 * @param msg
	 * @return
	 */
	public static <T> T notNull(T obj) {
		return notNull(obj, "The argument must not be null");
	}
	
	/**
	 * 不能为null
	 * @param <T>
	 * @param obj
	 * @param msg
	 * @return
	 */
	public static <T> T notNull(T obj, String msg) {
		if (obj == null)
			throw new RemexException(msg);
		return obj;
	}
	
	/**
	 * 不能为null
	 * @param <T>
	 * @param obj
	 * @return
	 */
	public static <T> T notNull(T obj, Consumer<T> action) {
		notNull(obj);
		action.accept(obj);
		return obj;
	}
	
	/**
	 * 不能为null
	 * @param <T>
	 * @param obj
	 * @param msg
	 * @return
	 */
	public static <T> T notNull(T obj, Consumer<T> action, String msg) {
		notNull(obj, msg);
		action.accept(obj);
		return obj;
	}
	

	/**
	 * 不能为null
	 * @param <T>
	 * @param <R>
	 * @param obj
	 * @param msg
	 * @param action
	 * @return
	 */
	public static <T, R> R notNullFn(T obj, Function<T, R> action) {
		notNull(obj);
		return action.apply(obj);
	}
	
	/**
	 * 不能为null
	 * @param <T>
	 * @param <R>
	 * @param obj
	 * @param msg
	 * @param action
	 * @return
	 */
	public static <T, R> R notNullFn(T obj, Function<T, R> action, String msg) {
		notNull(obj, msg);
		return action.apply(obj);
	}
	
	/**
	 * 不能为blank
	 * @param obj
	 * @param msg
	 * @return
	 */
	public static String notBlank(String obj) {
		return notBlank(obj, "The argument must not be blank");
	}
	
	/**
	 * 不能为blank
	 * @param obj
	 * @param msg
	 * @return
	 */
	public static String notBlank(String obj, String msg) {
		if (isBlank(obj))
			throw new RemexException(msg);
		return obj;
	}
	
	/**
	 * 不能为blank
	 * @param obj
	 * @return
	 */
	public static String notBlank(String obj, Consumer<String> action) {
		notBlank(obj);
		action.accept(obj);
		return obj;
	}
	
	/**
	 * 不能为blank
	 * @param obj
	 * @param msg
	 * @return
	 */
	public static String notBlank(String obj, Consumer<String> action, String msg) {
		notBlank(obj, msg);
		action.accept(obj);
		return obj;
	}

	/**
	 * 不能为blank
	 * @param <R>
	 * @param obj
	 * @param msg
	 * @param action
	 * @return
	 */
	public static <R> R notBlankFn(String obj, Function<String, R> action) {
		notBlank(obj);
		return action.apply(obj);
	}
	
	/**
	 * 不能为blank
	 * @param <R>
	 * @param obj
	 * @param msg
	 * @param action
	 * @return
	 */
	public static <R> R notBlankFn(String obj, Function<String, R> action, String msg) {
		notBlank(obj, msg);
		return action.apply(obj);
	}
	
	/**
	 * 不能为empty
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <T extends Collection<?>> T notEmpty(T obj, String msg) {
		if (isEmpty(obj))
			throw new RemexException(msg);
		return obj;
	}
	
	/**
	 * 不能为empty
	 * @param <T>
	 * @param obj
	 * @param action
	 */
	public static <T> T[] notEmpty(T[] obj, String msg) {
		if (isEmpty(obj))
			throw new RemexException(msg);
		return obj;
	}
	
	/**
	 * 不能相等
	 * @param <T>
	 * @param o1
	 * @param o2
	 * @param msg
	 */
	public static <T> void notEquals(T o1, T o2, String msg) {
		if (o1 == null) return;
		if (o2 == null) return;
		if (o1.equals(o2)) 
			throw new RemexException(msg);
	}
	
	
	
	/**
	 * 必须为null
	 * @param <T>
	 * @param obj
	 * @param msg
	 * @return
	 */
	public static <T> T mustNull(T obj, String msg) {
		if (obj != null)
			throw new RemexException(msg);
		return obj;
	}
	
	/**
	 * 必须为blank
	 * @param obj
	 * @param msg
	 * @return
	 */
	public static String mustBlank(String obj, String msg) {
		if (isNotBlank(obj))
			throw new RemexException(msg);
		return obj;
	}
	
	/**
	 * 必须相等
	 * @param <T>
	 * @param o1
	 * @param o2
	 * @param msg
	 */
	public static <T> void mustEquals(T o1, T o2, String msg) {
		if (o1 == null) return;
		if (o2 == null) return;
		if (!o1.equals(o2)) 
			throw new RemexException(msg);
	}

	
	
	/**
	 * 转String
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		return obj == null ? null : obj.toString();
	}
	
	/**
	 * 转List
	 * @param <T>
	 * @param arr
	 * @return
	 */
	public static <T> List<T> toList(T[] arr) {
		return ifNotNullFn(arr, o -> Arrays.stream(o).collect(Collectors.toList()));
	}
	
	/**
	 * 转List
	 * @param <T>
	 * @param coll
	 * @return
	 */
	public static <T> List<T> toList(Collection<T> coll) {
		return ifNotNullFn(coll, o -> o.stream().collect(Collectors.toList()));
	}
	
	/**
	 * 转Integer
	 * @param obj
	 * @return
	 */
	public static Integer toInteger(Object obj) {
		return obj == null ? null : Integer.valueOf(obj.toString());
	}
	
	/**
	 * 转Integer
	 * @param obj
	 * @return
	 */
	public static Long toLong(Object obj) {
		return obj == null ? null : Long.valueOf(obj.toString());
	}
	
	/**
	 * 转json，null => "{}"
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		return obj == null ? "{}" : JSONObject.toJSONString(obj);
	}
	
	
	
	
	
	
	/**
	 * trim字符串，并最后返回List
	 * @param coll
	 * @return
	 */
	public static List<String> trimToList(Collection<String> coll) {
		return ifNotNullFn(coll, o -> o.stream().map(String::trim).collect(Collectors.toList()));
	}
	
	/**
	 * 集合中是否有匹配上
	 * @param <T>
	 * @param coll
	 * @param action
	 * @return
	 */
	public static <T> boolean anyMatch(Collection<T> coll, Predicate<T> action) {
		if (isEmpty(coll)) return false;
		return coll.stream().anyMatch(action);
	}
	
	/**
	 * 取得map的key列表
	 * @param <T>
	 * @param map
	 * @return
	 */
	public static <T> List<T> keyList(Map<T, ?> map) {
		return Assist.forEachToList(map, (key, value) -> {
			return key;	
		});
	}
	
	/**
	 * 资源释放
	 * @param sources
	 */
	public static void close(Closeable...sources) {
		Assist.forEach(sources, source -> {
			try {
				source.close();
			} catch (Exception e) {
				logger.error("source close error!", e);
			}
		});
	}
}
