package com.legaldaily.estension.comment;

import java.util.HashMap;

import net.sf.ehcache.Ehcache;

import com.fzw.cache.CacheManager;
import com.fzw.utils.StringValueUtils;


/**
 * 根据提交的验证类型,验证值
 * @author hwj
 *
 */
public class ValidationServiceImpl implements ValidationService {

	
	@Override
	public HashMap valid(String type, String value, String tpgroup) {
		HashMap rv = null;;
		if("ip".equals(type)){
			rv = getIPCount(value,tpgroup);
		}else if ("idcard".equals(type)) {
			rv = getIDCardCount(value,tpgroup);
		}
		if(!rv.containsKey("count")){
			rv.put("count", "0");
		}
		return rv;
	}

	@Override
	public HashMap getIPCount(String ip, String tpgroup) {
		HashMap map = getHashMap(ipCache, ip, tpgroup);
		return map;
//		return StringValueUtils.getInt(String.valueOf(map.get("count")));
//		return CommentCache.getIntValue(ipCache, getValidCacheName(ip, tpgroup, time));
	}

	@Override
	public HashMap getIDCardCount(String idcard, String tpgroup) {
		HashMap map = getHashMap(idcardCache, idcard, tpgroup);
		return map;
//		return StringValueUtils.getInt(String.valueOf(map.get("count")));
//		return CommentCache.getIntValue(idcardCache, getValidCacheName(idcard, tpgroup, time));
	}

	@Override
	public boolean addIP(String ip, String tpgroup, String time) {
		HashMap map = getHashMap(ipCache, ip, tpgroup);
		int count = StringValueUtils.getInt(String.valueOf(map.get("count")));
		return setIPCount(ip, count+1,tpgroup,time);
	}

	@Override
	public boolean addIDCard(String idcard, String tpgroup, String time) {
		HashMap map = getHashMap(idcardCache, idcard, tpgroup);
		int count = StringValueUtils.getInt(String.valueOf(map.get("count")));
		return setIDCardCount(idcard, count+1,tpgroup,time);
	}

	@Override
	public boolean setIPCount(String ip, int count, String tpgroup, String time) {
		HashMap map = getHashMap(ipCache, ip, tpgroup);
		map.put("count", count);
		map.put("tpTime", time);
		return CacheManager.setToCache(getValidCacheName(ip, tpgroup), map, ipCache) ;
	}

	@Override
	public boolean setIDCardCount(String idcard, int count, String tpgroup, String time) {
		HashMap map = getHashMap(idcardCache, idcard, tpgroup);
		map.put("count", count);
		map.put("tpTime", time);
		return CacheManager.setToCache(getValidCacheName(idcard, tpgroup), map, idcardCache) ;
	}

	public String getValidCacheName(String value, String tpgroup){
		return value+tpgroup;
	}
	
	private HashMap getHashMap(Ehcache cache, String value, String tpgroup){
		HashMap map = null;
		String key = getValidCacheName(value, tpgroup);
		if(CacheManager.containKeyInCache(cache, key)){
			map = (HashMap) CacheManager.getValue(cache, key);
		}else{
			map = new HashMap();
		}
		return map;
	}
}
