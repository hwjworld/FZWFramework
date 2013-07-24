package com.fzw.repository.dao;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DaoConfig {

	// private static String resource =
	// "com/fzw/repository/dao/Configuration.xml";

	// private static SqlSessionFactory sessionFactory;

	// static {
	// try {
	// sessionFactory = newDaoManager();
	// } catch (Exception ex) {
	// throw new RuntimeException("Description.  Cause: " + ex, ex);
	// }
	// }

	// public static SqlSessionFactory getSessionFactory() {
	// return sessionFactory;
	// }

	// private static SqlSessionFactory newDaoManager() {
	// try {
	// Reader reader = Resources.getResourceAsReader(resource);
	// sessionFactory = new SqlSessionFactoryBuilder().build(reader);
	// } catch (IOException e) {
	// }
	// return sessionFactory;
	// }

	// new below
	public static SqlSessionFactory newDaoManager(String resource) {
		SqlSessionFactory sessionFactory = null;
		try {
			Reader reader = Resources.getResourceAsReader(resource);
			sessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (IOException e) {
		}
		return sessionFactory;
	}

	public static void setName(SqlSession session) {
		try {
			session.getConnection().prepareStatement("set names 'utf8'")
					.execute();
			session.getConnection()
					.prepareStatement("set character set 'utf8'").execute();
			session.getConnection()
					.prepareStatement("SET CHARACTER_SET_RESULTS=UTF8")
					.execute();
			session.getConnection()
					.prepareStatement(
							"SET collation_connection = 'utf8_general_ci'")
					.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
