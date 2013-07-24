/**
 * 因为存在0000-00-00 00:00:00这种情况自己需要处理一下异常
 */
package com.legaldaily.estension.ecard.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.ibatis.type.DateTypeHandler;

/**
 * @author hwj
 * 
 */
public class EcardDateTypeHandler extends DateTypeHandler {

	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		
		java.sql.Timestamp sqlTimestamp = null;
		try {
			sqlTimestamp = rs.getTimestamp(columnName);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		if (sqlTimestamp != null) {
			return new java.util.Date(sqlTimestamp.getTime());
		}
		return new java.util.Date(0);
	}

	public static void main(String[] args) {
		System.out.println(new Date(0));
	}
}
