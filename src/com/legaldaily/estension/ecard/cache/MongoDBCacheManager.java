package com.legaldaily.estension.ecard.cache;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDBCacheManager {
	private static Mongo mongo = null;
	
	public MongoDBCacheManager() throws UnknownHostException, MongoException {
		if(mongo == null)
			mongo = new Mongo("localhost",27017); 
	}

	public static Mongo getMongo() {
		return mongo;
	}

	public static void setMongo(Mongo mongo) {
		MongoDBCacheManager.mongo = mongo;
	}
	

}
