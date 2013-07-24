package com.legaldaily.estension.comment.yzm;

import java.util.Date;


public class Yzm {
	private String sid;
	private String pic;
	volatile Date time;

	public Yzm(String sid, String b64) {
		this.pic = b64;
		this.sid = sid;
		time = new Date();
	}

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}