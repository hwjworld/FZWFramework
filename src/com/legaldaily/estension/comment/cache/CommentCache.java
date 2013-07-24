package com.legaldaily.estension.comment.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import com.fzw.utils.StringValueUtils;

public class CommentCache{
	private static CacheManager singletonManager = CacheManager.create();
	
	public static String CACHE_IDCARD = "IDCARD";
	
	public static String CACHE_IP = "IP";
	
	public static void addCache(String cacheName){
		if(!singletonManager.cacheExists(cacheName)){
			 Cache cache = new Cache(new CacheConfiguration(cacheName, 100).maxElementsOnDisk(10000)
			 .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
			 .overflowToDisk(true).eternal(false).diskPersistent(false)
			 .diskExpiryThreadIntervalSeconds(0));
			singletonManager.addCache(cache);
		}
	}
	
	public static Ehcache getCache(String cacheName){
		return singletonManager.getCache(cacheName);
	}
	
	public static int getIntValue(Ehcache cache, String key){
		Object object = getValue(cache, key);
		int rv = 0;
		if(object != null){
			rv = StringValueUtils.getInt(object.toString());
		}
		return rv;
	}
	public static Object getValue(Ehcache cache, String key){
		return com.fzw.cache.CacheManager.getValue(cache, key);
	}
}
