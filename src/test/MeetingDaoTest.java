package test;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.mongodb.Mongo;

public class MeetingDaoTest {

    public static void main(String[] args) throws Exception {
        MeetingDaoTest test = new MeetingDaoTest();
         test.save();
        test.queryList();
         test.getByObjectId();
    }

    public static Datastore getDatastore() throws Exception {
        Mongo mongo = new Mongo("localhost", 27017);
        Morphia morphia = new Morphia();
//        Datastore ds = morphia.createDatastore(mongo, "my_mongo", "spell",
//                "007".toCharArray());
        Datastore ds = morphia.createDatastore(mongo, "my_mongo");
        return ds;
    }

    public void save() throws Exception {
        Datastore ds = MeetingDaoTest.getDatastore();
        Meeting m = new Meeting();
        m.setTime(new Date());
        m.setPlace("杭州");
        m.setTitle("游玩");
        ds.save(m);
        System.out.println("save success");
    }

    public void queryList() throws Exception {
        Datastore ds = MeetingDaoTest.getDatastore();
        List<Meeting> list = ds.find(Meeting.class).asList();

        /*
         * 也可以有更加高级的查询 List<Meeting> list =
         * ds.find(Meeting.class).field("place").endsWith("杭州").asList();
         */

        for (Meeting m : list) {
            System.out.println(m.getId() + " time:"
                    + m.getTime().toLocaleString());
        }

    }

    public void getByObjectId() throws Exception {
        Datastore ds = MeetingDaoTest.getDatastore();
        ObjectId id = new ObjectId("4e69bc872a6480d97bee7157");
        Meeting m = ds.get(Meeting.class, id);
        System.out.println(m.getTitle());
    }

}   