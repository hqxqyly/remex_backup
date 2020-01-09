package com.qyly.remex.mybatis.plus.injector;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.AbstractSqlInjector;
import com.baomidou.mybatisplus.core.injector.methods.Delete;
import com.baomidou.mybatisplus.core.injector.methods.DeleteBatchByIds;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import com.baomidou.mybatisplus.core.injector.methods.DeleteByMap;
import com.baomidou.mybatisplus.core.injector.methods.Insert;
import com.baomidou.mybatisplus.core.injector.methods.SelectBatchByIds;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.core.injector.methods.SelectByMap;
import com.baomidou.mybatisplus.core.injector.methods.SelectCount;
import com.baomidou.mybatisplus.core.injector.methods.SelectList;
import com.baomidou.mybatisplus.core.injector.methods.SelectMaps;
import com.baomidou.mybatisplus.core.injector.methods.SelectMapsPage;
import com.baomidou.mybatisplus.core.injector.methods.SelectObjs;
import com.baomidou.mybatisplus.core.injector.methods.SelectOne;
import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.qyly.remex.mybatis.plus.injector.methods.DeleteByKey;
import com.qyly.remex.mybatis.plus.injector.methods.RemexUpdate;
import com.qyly.remex.mybatis.plus.injector.methods.RemexUpdateById;
import com.qyly.remex.mybatis.plus.injector.methods.SelectByEntity;
import com.qyly.remex.mybatis.plus.injector.methods.SelectByKey;
import com.qyly.remex.mybatis.plus.injector.methods.SelectByKeyAsc;
import com.qyly.remex.mybatis.plus.injector.methods.SelectByKeyDesc;
import com.qyly.remex.mybatis.plus.injector.methods.SelectOneByKey;
import com.qyly.remex.mybatis.plus.injector.methods.SelectPageByEntity;
import com.qyly.remex.mybatis.plus.injector.methods.UpdateByKey;

/**
 * sql解析注册器
 * 
 * @author Qiaoxin.Hong
 *
 */
public class RemexSqlInjector extends AbstractSqlInjector {

	@Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        return Stream.of(
            new Insert(),
            new Delete(),
            new DeleteByMap(),
            new DeleteById(),
            new DeleteBatchByIds(),
//            new Update(),
//            new UpdateById(),
            new SelectById(),
            new SelectBatchByIds(),
            new SelectByMap(),
            new SelectOne(),
            new SelectCount(),
            new SelectMaps(),
            new SelectMapsPage(),
            new SelectObjs(),
            new SelectList(),
            new SelectPage(),
            
            //remex重写的方法
            new RemexUpdate(),
            new RemexUpdateById(),
            
            //remex新增的方法
            new SelectByKey(),
            new SelectByKeyAsc(),
            new SelectByKeyDesc(),
            new SelectOneByKey(),
            new DeleteByKey(),
            new UpdateByKey(),
            new SelectByEntity(),
            new SelectPageByEntity()
        ).collect(toList());
    }
}
