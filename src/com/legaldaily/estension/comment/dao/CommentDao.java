package com.legaldaily.estension.comment.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSessionFactory;

import com.fzw.repository.dao.AbstractFZWDao;
import com.legaldaily.estension.comment.Constants;

public class CommentDao extends AbstractFZWDao{

	private static SqlSessionFactory sessionFactory = Constants.EXTENSION.getDbSessionFactory();
	
	public CommentDao() {
		sessionFactory = Constants.EXTENSION.getDbSessionFactory();
	}
	public List<Map> selectAllIDCardInfo(){
		return  (List<Map>) selectList(sessionFactory, "selectAllIDcard");
	}
	
	public List<Map> selectAllIPInfo(){
		return  (List<Map>) selectList(sessionFactory,"selectAllIP");
	}
}
