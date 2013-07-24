package com.legaldaily.estension.comment;

import java.util.List;
import java.util.Map;

import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.comment.cache.CommentCache;
import com.legaldaily.estension.comment.dao.CommentDao;

public class CommentInit {
	static{
		CommentCache.addCache(CommentCache.CACHE_IDCARD);
		CommentCache.addCache(CommentCache.CACHE_IP);
		initData();
	}
	/**
	 * ip data and idcard data
	 */
	private static void initData() {
		CommentDao dao = new CommentDao();
		ValidationService service = new ValidationServiceImpl();
		List<Map> idcards = dao.selectAllIDCardInfo();
		for (Map map : idcards) {
			String key = String.valueOf(map.get("user_id"));
			int count = StringValueUtils.getInt(String.valueOf(map.get("count")));
			String tpgroup = String.valueOf(map.get("tp_group"));
			String tpTime = String.valueOf(map.get("tpTime"));
			service.setIDCardCount(key, count, tpgroup, tpTime);
		}
		
		List<Map> ips = dao.selectAllIPInfo();		
		for (Map map : ips) {
			String key = String.valueOf(map.get("user_ip"));
			int count = StringValueUtils.getInt(String.valueOf(map.get("count")));
			String tpgroup = String.valueOf(map.get("tp_group"));
			String tpTime = String.valueOf(map.get("tpTime"));
			service.setIPCount(key, count, tpgroup, tpTime);
		}
	}
	
}
