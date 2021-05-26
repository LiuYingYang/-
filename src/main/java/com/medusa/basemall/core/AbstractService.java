package com.medusa.basemall.core;


import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

	@Autowired
	protected Mapper<T> mapper;

	private Class<T> modelClass;    // 当前泛型真实类型的Class

	public AbstractService() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		modelClass = (Class<T>) pt.getActualTypeArguments()[0];
	}

	@Override
	public int save(T model) {
		int i = mapper.insertSelective(model);
		return i;
	}

	@Override
	public int save(List<T> models) {
		int i = mapper.insertList(models);
		return i;
	}

	@Override
	public int deleteById(Integer id) {
		int i = mapper.deleteByPrimaryKey(id);
		return i;
	}

	@Override
	public int deleteById(Long id) {
		int i = mapper.deleteByPrimaryKey(id);
		return i;
	}

	@Override
	public int deleteByIds(String ids) {
		int i = mapper.deleteByIds(ids);
		return i;
	}

	@Override
	public int update(T model) {
		int i = mapper.updateByPrimaryKeySelective(model);
		return i;
	}

	@Override
	public T findById(Long id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public T findById(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public T findBy(String fieldName, Object value) throws TooManyResultsException {
		try {
			T model = modelClass.newInstance();
			Field field = modelClass.getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(model, value);
			return mapper.selectOne(model);
		} catch (ReflectiveOperationException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public T findBy(String[] fieldNames, Object[] value) throws TooManyResultsException {
		try {
			T model = modelClass.newInstance();
			for (int i = 0; i < fieldNames.length; i++) {
				Field field = modelClass.getDeclaredField(fieldNames[i]);
				field.setAccessible(true);
				field.set(model, value[i]);
			}
			return mapper.selectOne(model);
		} catch (ReflectiveOperationException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<T> findByList(String fieldName, Object value) throws TooManyResultsException {
		try {
			String[] split = value.toString().split(",");
			if (split.length == 0) {
				throw new ServiceException("查询字段value 不能为空");
			}
			T model = modelClass.newInstance();
			Condition condition = new Condition(model.getClass());
			condition.createCriteria().andIn(fieldName, Arrays.asList(split));
			List<T> ts = mapper.selectByCondition(condition);
			return ts;
		} catch (ReflectiveOperationException e) {
			throw new ServiceException(e.getMessage(), e);
		}
	}

	@Override
	public List<T> findByIds(String ids) {
		return mapper.selectByIds(ids);
	}

	@Override
	public List<T> findByCondition(Condition condition) {
		return mapper.selectByCondition(condition);
	}

	@Override
	public T findByOneCondition(Condition condition) {
		List<T> ts = mapper.selectByCondition(condition);
		if (ts != null && ts.size() == 1) {
			return ts.get(0);
		}
		return null;
	}

	@Override
	public List<T> findAll() {
		return mapper.selectAll();
	}
}
