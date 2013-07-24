package com.legaldaily.estension.ecard.model.user;

public class Score {
	private User user;
	private long totalScore;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public long getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(long totalScore) {
		this.totalScore = totalScore;
	}
}