package com.qyly.remex.mybatis.plus.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;

/**
 * 扩展BaseMethod，提供一些新的方法
 * 
 * @see com.smart.remex.mybatis.plus.injector.methods.BaseMethod
 * 
 * @author Qiaoxin.Hong
 *
 * @param <T>
 */
public interface RemexBaseMapper<T> extends BaseMapper<T> {

	/**
	 * 根据某字段值查询数据列表
	 * @param key
	 * @param value
	 * @return
	 */
	List<T> selectByKey(@Param("key") String key, @Param("value") String value);
	
	/**
	 * 根据某字段值查询数据列表并升序
	 * @param key
	 * @param value
	 * @return
	 */
	List<T> selectByKeyAsc(@Param("key") String key, @Param("value") String value, @Param("order") String order);
	
	/**
	 * 根据某字段值查询数据列表并降序
	 * @param key
	 * @param value
	 * @return
	 */
	List<T> selectByKeyDesc(@Param("key") String key, @Param("value") String value, @Param("order") String order);
	
	/**
	 * 根据某字段值查询单条数据
	 * @param key
	 * @param value
	 * @return
	 */
	T selectOneByKey(@Param("key") String key, @Param("value") String value);
	
	/**
	 * 根据某字段值删除数据
	 * @param key
	 * @param value
	 * @return
	 */
	int deleteByKey(@Param("key") String key, @Param("value") String value);
	
	/**
	 * 根据某字段值修改数据
	 * @param entity
	 * @param key
	 * @return
	 */
	int updateByKey(@Param(Constants.ENTITY) T entity, @Param("key") String key, @Param("value") String value);
	
	/**
	 * 根据条件查询数据列表
	 * @param entity
	 * @return
	 */
	List<T> selectByEntity(@Param(Constants.ENTITY)T entity);
	
	/**
	 * 根据条件分页查询数据列表
	 * @param entity
	 * @return
	 */
	IPage<T> selectPageByEntity(IPage<T> page, @Param(Constants.ENTITY)T entity);
}
