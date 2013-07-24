package com.fzw.utils;

import java.util.List;

public class ListUtils extends org.apache.commons.collections.ListUtils {

	/**
	 * 截取list中的子列表
	 * @param list
	 * @param from 如果小于0，则视为从头开始截取
	 * @param to 如果大于总长度或<=0，都视为截取到最后
	 * @return
	 */
	public static List<? extends Object> subList(List<? extends Object> list,
			int from, int to) {
		if(from >= to){
			return ListUtils.EMPTY_LIST;
		}
		if (to > list.size() || to <= 0) {
			to = list.size();
		}
		if(from<=0){
			from = 0;
		}
		return list.subList(from, to);
	}
	public static String[] list2stringArray(List<? extends Object> list){
		String[] rv = new String[list.size()];
		list.toArray(rv);
		return rv;
	}
	

}
