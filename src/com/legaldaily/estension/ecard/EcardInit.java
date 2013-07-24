package com.legaldaily.estension.ecard;

import com.fzw.utils.LogUtils;
import com.legaldaily.estension.ecard.cache.EcardCacheManager;
import com.legaldaily.estension.ecard.cache.LatestSolvedQuestionIndex;
import com.legaldaily.estension.ecard.cache.PostIndex;
import com.legaldaily.estension.ecard.cache.RankCache;

public class EcardInit {
	static {
		LogUtils.info("法律问答服务(Ecard)插件初始化。");
		EcardCacheManager.initCaches();
		LogUtils.info("载入缓存完成");
		PostIndex.init();
		LatestSolvedQuestionIndex.init();
		LogUtils.info("载入索引数据完成");
		RankCache.init();
		LogUtils.info("法律问答服务(Ecard)初始化结束。");
	}

}
