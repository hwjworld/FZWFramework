package com.legaldaily.estension.ecard.repository.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.terracotta.ehcachedx.org.mortbay.jetty.servlet.AbstractSessionManager.Session;

import com.fzw.repository.dao.AbstractFZWDao;
import com.legaldaily.estension.ecard.Constants;
import com.legaldaily.estension.ecard.model.condition.EcardServiceCondition;

public abstract class EcardDao extends AbstractFZWDao {

	protected static SqlSessionFactory sessionFactory = Constants.EXTENSION.getDbSessionFactory();
	public static final QuestionDao QUESTION_DAO = new QuestionDaoSql();
	public static final AnswerDao ANSWER_DAO = new AnswerDaoSql();
	public static final PostDao POST_DAO = new PostDaoSql();
	public static final UserDao USER_DAO = new UserDaoSql();
	public static final LawDao LAW_DAO = new LawDaoSql();
	public static final AreaDao AREA_DAO = new AreaDaoSql();

	protected static final Map<String, Long> countMap = new HashMap<String, Long>();

	/**
	 * 用于返回mysql结果list及总量的count的,使用了mysql SELECT FOUND_ROWS()特性
	 * statement[0]中必须要包含SQL_CALC_FOUND_ROWS,否则数量为0
	 * 
	 * @param statement
	 * @return Object[2]=[List,Integer]
	 */
	protected Object[] selectListWithCount(String statement, Object parameter) {
		if (StringUtils.isBlank(statement)) {
			return ArrayUtils.EMPTY_OBJECT_ARRAY;
		}
		Object[] result = new Object[2];
		SqlSession session = sessionFactory.openSession();
		try {
			result[0] = session.selectList(statement, parameter);
			// if(StringUtils.indexOf(statement,"SQL_CALC_FOUND_ROWS")!=StringUtils.INDEX_NOT_FOUND){
			result[1] = (Long) session.selectOne("foundrows");
			if (parameter instanceof EcardServiceCondition) {
				String messagestr = StringUtils.lowerCase(((EcardServiceCondition) parameter).getMessage()
						.getMessageStr());
				String messagename = StringUtils.lowerCase(((EcardServiceCondition) parameter).getMessage()
						.getMessageName());
				messagestr = StringUtils.replaceOnce(messagestr, messagename, messagename+"count");
				countMap.put(messagestr, (Long)result[1]);
			}
			// }else {
			// result[1] = 0;
			// }
		} finally {
			session.close();
		}
		return result;
	}

	protected List<? extends Object> selectList( String statement) {
		return super.selectList(sessionFactory, statement);
	}
	
	protected List<? extends Object> selectList(String statement, Object parameter) {
		return super.selectList(sessionFactory, statement, parameter);
	}

	protected Object selectOne(String statement) {
		return super.selectOne(sessionFactory, statement);
	}

	protected Object selectOne(String statement, Object parameter) {
		return super.selectOne(sessionFactory, statement, parameter);
	}
	
	public int update(String statement) {
		return super.update(sessionFactory, statement);
	}
	public int update(String statement,	Object parameter) {
		return super.update(sessionFactory, statement, parameter);
	}

	/**
	 * 
	 * @param statement
	 * @return last mysql insert id
	 */
	public int insert(String statement) {
		return insert( statement, null);
	}

	/**
	 * 
	 * @param statement
	 * @param parameter
	 * @return last mysql insert id
	 */
	public int insert( String statement,Object parameter) {
		SqlSession session = sessionFactory.openSession(ExecutorType.REUSE);
		try {
			int i = session.insert(statement, parameter);
			if(i >0){
				return getMysqlLastId(session);
			}else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			session.close();
		}
	}

	private int getMysqlLastId(SqlSession session) {
		return (Integer)session.selectOne("mysqllastid");
//		return (Integer) selectOne(sessionFactory,"mysqllastid");
	}
	public int delete(String statement) {
		return super.delete(sessionFactory, statement);
	}
	public int delete( String statement, Object parameter) {
		return super.delete(sessionFactory, statement, parameter);
	}

	public static long getCount(EcardServiceCondition condition) {
		long count = 0;
		String key = StringUtils.lowerCase(condition.getMessage().getMessageStr());
		if (countMap.containsKey(key)) {
			count = countMap.get(key);
			countMap.remove(key);
		}
		return count;
	}
	
}
