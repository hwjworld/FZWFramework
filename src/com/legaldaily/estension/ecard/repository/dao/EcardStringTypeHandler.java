package com.legaldaily.estension.ecard.repository.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.StringTypeHandler;

public class EcardStringTypeHandler extends StringTypeHandler {

	  public Object getNullableResult(ResultSet rs, String columnName)
	      throws SQLException {
		  try {
			    return rs.getString(columnName);			
		} catch (Exception e) {
		    return "Error post_content, id:"+rs.getString("ID");
		}
	  }
}
