package com.qyly.remex.utils;

import java.util.UUID;

/**
 * ID工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class IdUtils {

	/**
	 * 创建主键编号
	 * @return
	 */
	public static String createId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
