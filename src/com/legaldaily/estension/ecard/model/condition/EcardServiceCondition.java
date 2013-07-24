package com.legaldaily.estension.ecard.model.condition;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.fzw.model.ConnectionMessage;
import com.fzw.model.condition.ServiceCondition;
import com.fzw.utils.ListUtils;
import com.fzw.utils.StringValueUtils;

public abstract class EcardServiceCondition extends ServiceCondition {

	public EcardServiceCondition() {
		super();
	}
	public EcardServiceCondition(ConnectionMessage message){
		super(message);
	}
	
	/**
	 * 生成类似field,asc,field,desc这样的数组 [field],[desc|asc]...
	 * 
	 * @param orderby
	 * @return
	 */
	public static String[] getOrderBy(String orderby) {
		if (StringUtils.isBlank(orderby)) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		CollectionUtils.addAll(list, StringUtils.split(orderby, ","));
		for (int i = 1; i < list.size(); i += 2) {
			if (!"desc".equalsIgnoreCase(list.get(i))
					&& !"asc".equalsIgnoreCase(list.get(i))) {
				list.add(i, "desc");
			}
		}
		return ListUtils.list2stringArray(list);
	}

	/**
	 * 生成sql语句格式的order by
	 * 
	 * @param orderby
	 * @return
	 */
	public static String getOrderBySql(String orderby) {
		if (StringUtils.isBlank(orderby)) {
			return null;
		}
		String[] order = getOrderBy(orderby);
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < order.length; i += 2) {
			buffer.append(order[i]).append(" ").append(order[i + 1])
					.append(" ,");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		return buffer.toString();
	}

	/**
	 * [flag][value] like [=][3]
	 * 
	 * @param string
	 * @return [mark], [value]
	 */
	public int[] getComparativeFiled(String string) {
		if (StringUtils.isBlank(string) || string.length() < 1) {
			return null;
		}
		int[] rv = new int[2];
		if (string.charAt(0) == '=') {
			rv[0] = 1;
			rv[1] = StringValueUtils.getInt(string.substring(1));
		} else if (string.charAt(0) == '<') {
			rv[0] = 2;
			rv[1] = StringValueUtils.getInt(string.substring(1));
		} else if (string.charAt(0) == '>') {
			rv[0] = 3;
			rv[1] = StringValueUtils.getInt(string.substring(1));
		} else {
			rv[0] = 1;
			rv[1] = StringValueUtils.getInt(string);
		}
		return rv;
	}

	public static String[] getIds(String string) {
		if(StringUtils.isBlank(string))
			return null;
		String sep = ";";
		if(string.indexOf(",") >= 0){
			sep = ",";
		}
		return StringUtils.split(string, sep);
	}

	public static int[] getIdsInt(String string) {
		return StringValueUtils.convert2intArrays(getIds(string));
	}
	public static long[] getIdsLong(String string) {
		return StringValueUtils.convert2longArrays(getIds(string));
	}
}
