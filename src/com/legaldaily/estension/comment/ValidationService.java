package com.legaldaily.estension.comment;

import java.util.HashMap;

import net.sf.ehcache.Ehcache;

import com.fzw.connection.FZWService;
import com.legaldaily.estension.comment.cache.CommentCache;

public interface ValidationService extends FZWService{
	Ehcache ipCache = CommentCache.getCache(CommentCache.CACHE_IP);
	Ehcache idcardCache = CommentCache.getCache(CommentCache.CACHE_IDCARD);
	/**
	 * 验证值出现的次数
	 * @param type
	 * param value
	 * @return
	 */
	public HashMap valid(String type, String value, String tpgroup);
	
	public HashMap getIPCount(String ip, String tpgroup);
	public HashMap getIDCardCount(String idcard, String tpgroup);
	public boolean addIP(String ip, String tpgroup, String time);
	public boolean addIDCard(String idcard, String tpgroup, String time);
	public boolean setIPCount(String ip, int count, String tpgroup, String time);
	public boolean setIDCardCount(String idcard, int count, String tpgroup, String time);
}
