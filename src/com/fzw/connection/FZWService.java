package com.fzw.connection;

import com.fzw.domain.DomainEvents;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * message service task dispenser 根据消息分发具体服务任务
 * 
 * @author hwj
 * 
 */
public interface FZWService {
	public static final DomainEvents DOMAIN_EVENTS = new DomainEvents();
	public static final Gson gson = new GsonBuilder().registerTypeAdapter(Object.class,
			new NaturalDeserializer()).create();

}
