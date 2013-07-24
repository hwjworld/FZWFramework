package com.legaldaily.estension.ecard.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.LongTypeHandler;

public class EcardLongTypeHandler extends LongTypeHandler {

	@Override
	public Object getNullableResult(ResultSet rs, String columnName)
			throws SQLException {
		try {
			return rs.getLong(columnName);
		} catch (Exception e) {
			return Long.MAX_VALUE;
		}
	}
}
