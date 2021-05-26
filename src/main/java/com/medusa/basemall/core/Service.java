package com.medusa.basemall.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * service 层 基础接口，其他Service 接口 请继承该接口
 */
public interface Service<T> {

    int save(T model);//持久化

    int save(List<T> models);//批量持久化

    int deleteById(Integer id);//通过主鍵刪除

    int deleteById(Long id);//通过主鍵刪除

    int deleteByIds(String ids);//批量刪除 eg：ids -> “1,2,3,4”

    int update(T model);//更新

    T findById(Long id);//通过ID查找

    T findById(Integer id);//通过ID查找

	/**
	 * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value需符合unique约束
	 * @param fieldName
	 * @param value
	 * @return
	 * @throws TooManyResultsException
	 */
    T findBy(String fieldName, Object value) throws TooManyResultsException;

	T findBy(String[] fieldNames, Object[] value) throws TooManyResultsException;


	/**
	 * 通过Model中某个成员变量名称（非数据表中column的名称）查找,value为逗号分隔
	 * @param fieldName
	 * @param value
	 * @return
	 * @throws TooManyResultsException
	 */
    List<T> findByList(String fieldName, Object value) throws TooManyResultsException;

    List<T> findByIds(String ids);//通过多个ID查找//eg：ids -> “1,2,3,4”

    List<T> findByCondition(Condition condition);//根据条件查找(返回多个)

	T findByOneCondition(Condition condition);//根据条件查找(返回一个)

    List<T> findAll();//获取所有
}
