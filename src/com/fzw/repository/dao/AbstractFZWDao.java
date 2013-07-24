package com.fzw.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

public abstract class AbstractFZWDao {
	
	protected Object selectOne(SqlSessionFactory sessionFactory, String statement) {
		return selectOne(sessionFactory,statement, null);
	}

	protected Object selectOne(SqlSessionFactory sessionFactory, String statement, Object parameter) {
		Object object = 0;
		SqlSession session = sessionFactory.openSession(ExecutorType.REUSE);
		try {
			object = session.selectOne(statement, parameter);
		} finally {
			session.close();
		}
		return object;
	}

	protected List<? extends Object> selectList(SqlSessionFactory sessionFactory, String statement) {
		return selectList(sessionFactory,statement, null, RowBounds.DEFAULT);
	}

	protected List<? extends Object> selectList(SqlSessionFactory sessionFactory, String statement,
			Object parameter) {
		return selectList(sessionFactory,statement, parameter, RowBounds.DEFAULT);
	}

	@SuppressWarnings("unchecked")
	protected List<? extends Object> selectList(SqlSessionFactory sessionFactory, String statement,
			Object parameter, RowBounds rowBounds) {
		List<Object> object = null;
		SqlSession session = sessionFactory.openSession(ExecutorType.REUSE);
		try {
			object = session.selectList(statement, parameter, rowBounds);
		} finally {
			session.close();
		}
		return object;
	}

	public int insert(SqlSessionFactory sessionFactory, String statement) {
		return insert(sessionFactory,statement, null);
	}

	public int insert(SqlSessionFactory sessionFactory, String statement, Object parameter) {

		SqlSession session = sessionFactory.openSession(ExecutorType.REUSE);
		try {
			return session.insert(statement, parameter);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}

	public int update(SqlSessionFactory sessionFactory, String statement) {
		return update(sessionFactory,statement, null);
	}

	public int update(SqlSessionFactory sessionFactory, String statement, Object parameter) {

		SqlSession session = sessionFactory.openSession(ExecutorType.REUSE);
		try {
			return session.update(statement, parameter);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return 0;
		} finally {
			session.close();
		}
	}

	public int delete(SqlSessionFactory sessionFactory, String statement) {
		return delete(sessionFactory,statement, null);
	}

	public int delete(SqlSessionFactory sessionFactory, String statement, Object parameter) {

		SqlSession session = sessionFactory.openSession(ExecutorType.REUSE);
		try {
			return session.delete(statement, parameter);
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return 0;
		} finally {
			session.close();
		}
	}
	/**
	 * 返回供mabatis读取的参数,支持两个参数,以value做数组名
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static Map genMabatisMapValues(Object ...value){
		Map map = new HashMap();
		for (int i = 0; i < value.length; i++) {
			map.put("value"+i, value[i]);
		}
		return map;
	}
	/**
	 * 返回供mabatis读取的参数,支持两个参数,以key和value做键值
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static Map genMabatisMapParameter(Object key, Object value){
		Map map = new HashMap();
		map.put("key", key);
		map.put("value", value);
		return map;
	}
}
