package test;

import java.net.UnknownHostException;
import java.util.Set;

import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

public class MongoDbTest {
	public static void main(String[] args) {
		try {
			Mongo mongo = new Mongo("127.0.0.1",27017);
			
			System.out.println(mongo.getDatabaseNames().toString());
			DB db = mongo.getDB("test");
			System.out.println(db);
			Set<String> colls = db.getCollectionNames();
			for (String string : colls) {
				System.out.println(string);
			}
			DBCollection collection = db.getCollection("foo");
			BasicDBObject doc = new BasicDBObject();
			doc.put("113", "34");
			System.out.println(collection.findOne(doc));
//			doc.put("name", "mongo");
//			doc.put("size", "123");
//			BasicDBObject inf = new BasicDBObject();
//			inf.put("sub", "subname");
//			inf.put("ss", "234");
//			doc.put("inf", inf);
//			collection.insert(doc);
//			for (int i=0; i < 10; i++) {
//			    collection.insert(new BasicDBObject().append("i", i));
//			}
//			DBCursor cur = collection.find();
//			cur.close();
//			while (cur.hasNext()) {
//				System.out.println(cur.next());
//			}
//			System.out.println(cur);
//			System.out.println(collection.getCount());
			
			
//			BasicDBObject query = new BasicDBObject();
//			 query.put("i", new BasicDBObject("$gt", 8));
//			cur = collection.find(query);
//			System.out.println("---------");
//			while(cur.hasNext()) {
//	            System.out.println(cur.next());
//	        }
			
			mongo.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
