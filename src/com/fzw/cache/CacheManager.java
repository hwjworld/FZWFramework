package com.fzw.cache;

import java.io.Serializable;

import com.legaldaily.estension.ecard.cache.EcardCacheManager;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class CacheManager {

	/**
	 * 将对象存入指定cache中,如已存在将不覆盖
	 * 
	 * @return
	 * @see 需覆盖请看setToCache()
	 */
	public static void putToCache(Serializable key, Serializable value,
			Ehcache cache) {
		Element elem = cache.get(key);
		if (elem == null) {
			cache.put(elem = new Element(key, value));
		}
		return;
	}

	/**
	 * 从cache中移除元素
	 * 
	 * @return
	 */
	public static void removeFromCache(Serializable key, Ehcache cache) {
		cache.remove(key);
		return;
	}

	/**
	 * 将对象存入指定cache中,如已存在将覆盖
	 * 
	 * @return
	 * @see 不需覆盖请看putToCache()
	 */
	public static boolean setToCache(Serializable key, Serializable value,
			Ehcache cache) {
		boolean rv = true;
		try{
			cache.put(new Element(key, value));
		}catch (Exception e) {
			rv = false;
		}
		return rv;
	}

	/**
	 * 将对象存入指定cache中,如已存在将不覆盖
	 * 
	 * @return
	 * @see 需覆盖请看setToCache()
	 */
	public static void clearCache(Ehcache cache) {
		cache.removeAll();
		return;
	}

	public static boolean containKeyInCache(Ehcache cache, Serializable key) {
		Object object = EcardCacheManager.getValue(cache, key);
		if(object == null)
			return false;
		else {
			return true;
		}
	}
	
	public static Object getValue(Ehcache cache, Serializable key) {
		
		Element elem = cache.get(key);
		Object value = null;
		if (elem == null) {
			return null;
		} else {
			value = elem.getValue();
		}
		return value;
	}
}
