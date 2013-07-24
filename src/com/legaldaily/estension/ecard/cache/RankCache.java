package com.legaldaily.estension.ecard.cache;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.collections.CollectionUtils;

import com.fzw.Globals;
import com.fzw.utils.ListUtils;
import com.fzw.utils.LogUtils;
import com.fzw.utils.StringValueUtils;
import com.legaldaily.estension.ecard.model.condition.UserCondition;
import com.legaldaily.estension.ecard.model.user.Score;
import com.legaldaily.estension.ecard.repository.UserRepository;
import com.legaldaily.estension.ecard.service.users.UserServices;
import com.legaldaily.estension.ecard.service.users.UserServicesImpl;

public class RankCache {
	private static Map<Integer, List<Score>> rankCache = new HashMap<Integer, List<Score>>();
	
	public static final int YESTERDAY_RANK = 1;
	public static final int LASTWEEK_RANK = 2;
	public static final int TOTAL_RANK = 3;
	
	private static boolean inited = false;
	
	private static final UserServices USER_SERVICES = new UserServicesImpl((UserRepository) Globals.getBean("userRepository"));
	public static void init(){
		if(!inited){
			LogUtils.info("准备排行数据");
			Timer timer = new Timer();
			timer.schedule(new UpdateScoreRankTask(), 0, 30*60*1000);
		}
	}

	static class UpdateScoreRankTask extends TimerTask{
		@Override
		public void run() {
			UserCondition condition = new UserCondition();
			condition.setCount(10);
			
			Calendar start = Calendar.getInstance();
			Calendar end = Calendar.getInstance();
			start.set(Calendar.DAY_OF_MONTH, start.get(Calendar.DAY_OF_MONTH)-1);
			
			condition.setStarttime(start.getTime());
			condition.setEndtime(end.getTime());
			rankCache.put(YESTERDAY_RANK, USER_SERVICES.getScoreRank(condition));
			
			start.set(Calendar.DAY_OF_MONTH, start.get(Calendar.DAY_OF_MONTH)-7);
			end.set(Calendar.DAY_OF_MONTH, end.get(Calendar.DAY_OF_MONTH)-1);
			condition.setStarttime(start.getTime());
			condition.setEndtime(end.getTime());
			rankCache.put(LASTWEEK_RANK, USER_SERVICES.getScoreRank(condition));
			
			condition.setEndtime(null);
			condition.setStarttime(null);
			rankCache.put(TOTAL_RANK, USER_SERVICES.getScoreRank(condition));
			LogUtils.info("准备排行数据完毕");
		}		
	}
	
	public static List<Score> getRankScore(UserCondition condition){
		int type = StringValueUtils.getInt(condition.getMessage().getParamMap().get("type"));
		List<Score> scores = new ArrayList<Score>();
		List<Score> tmp = rankCache.get(type);
		if(tmp == null){
			scores = USER_SERVICES.getScoreRank(condition);
		}else if(CollectionUtils.isNotEmpty(tmp)){
			scores.addAll(tmp);
			scores = (List<Score>) ListUtils.subList(scores, 0, condition.getCount());
		}
		return scores;
	}
}
