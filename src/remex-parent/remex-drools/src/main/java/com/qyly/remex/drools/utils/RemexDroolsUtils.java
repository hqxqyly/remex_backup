package com.qyly.remex.drools.utils;

import java.util.HashMap;
import java.util.Map;

import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import com.qyly.remex.utils.Assist;

/**
 * Drools工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexDroolsUtils {
	
	/** 系统全局公共参数 */
	protected static Map<String, Object> sysGlobalParams = new HashMap<>();

	/**
	 * 规则执行
	 * @param rule
	 */
	public static void execute(String rule) {
		execute(rule, null, null);
	}
	
	/**
	 * 规则执行
	 * @param rule 规则
	 * @param params 参数
	 * @param globalParams 公共参数
	 */
	public static void execute(String rule, Map<String, Object> params, Map<String, Object> globalParams) {
		if (Assist.isBlank(rule)) return;
		
		KieHelper kieHelper = new KieHelper();
		kieHelper.addContent(rule, ResourceType.DRL);
		KieSession kieSession = kieHelper.build().newKieSession();
		
		
		//系统全局公共参数
		Assist.forEach(sysGlobalParams, kieSession::setGlobal);
		
		//公共参数
		Assist.forEach(globalParams, kieSession::setGlobal);
		
		//参数
		Assist.ifNotEmpty(params, kieSession::insert);
		
		//规则执行
		kieSession.fireAllRules();
	}
	
	
	
	
	/**
	 * 添加系统全局公共参数
	 */
	public static void addSysGlobalParam(String key, Object value) {
		sysGlobalParams.put(key, value);
	}
	
	
	
	public static Map<String, Object> getSysGlobalParams() {
		return sysGlobalParams;
	}
	
	public static void setSysGlobalParams(Map<String, Object> sysGlobalParams) {
		RemexDroolsUtils.sysGlobalParams = sysGlobalParams;
	}
}
