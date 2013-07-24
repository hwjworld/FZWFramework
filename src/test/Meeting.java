package test;

import java.util.Date;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity
//默认是要持久所有对象的
public class Meeting {
  @Id
  private ObjectId id;

  private static final long serialVersionUID = -4161545150796484674L;
  // 标题
  // @Transient //这个表示不持久，莫非
  private String title;
  // 地点
  private String place;
  // 时间
  private Date time;

  public String getTitle() {
      return title;
  }

  public void setTitle(String title) {
      this.title = title;
  }

  public String getPlace() {
      return place;
  }

  public void setPlace(String place) {
      this.place = place;
  }

  public Date getTime() {
      return time;
  }

  public void setTime(Date time) {
      this.time = time;
  }

  public ObjectId getId() {
      return id;
  }

  public void setId(ObjectId id) {
      this.id = id;
  }
}      

