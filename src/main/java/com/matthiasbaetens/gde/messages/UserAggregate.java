package com.matthiasbaetens.gde.messages;

import org.apache.beam.sdk.transforms.windowing.Window;

public class UserAggregate {
	Window window;
	String userId;
	Integer userCount;
	Integer numberOfLanguages;
	Integer numberOfSentences;
	Float averageTimeBetween;
	Float totalSessionLength;

	public UserAggregate(Window window, String userId, Integer userCount, Integer numberOfLanguages,
			Integer numberOfSentences, Float averageTimeBetween, Float totalSessionLength) {
		super();
		this.window = window;
		this.userId = userId;
		this.userCount = userCount;
		this.numberOfLanguages = numberOfLanguages;
		this.numberOfSentences = numberOfSentences;
		this.averageTimeBetween = averageTimeBetween;
		this.totalSessionLength = totalSessionLength;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
		this.window = window;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getUserCount() {
		return userCount;
	}

	public void setUserCount(Integer userCount) {
		this.userCount = userCount;
	}

	public Integer getNumberOfLanguages() {
		return numberOfLanguages;
	}

	public void setNumberOfLanguages(Integer numberOfLanguages) {
		this.numberOfLanguages = numberOfLanguages;
	}

	public Integer getNumberOfSentences() {
		return numberOfSentences;
	}

	public void setNumberOfSentences(Integer numberOfSentences) {
		this.numberOfSentences = numberOfSentences;
	}

	public Float getAverageTimeBetween() {
		return averageTimeBetween;
	}

	public void setAverageTimeBetween(Float averageTimeBetween) {
		this.averageTimeBetween = averageTimeBetween;
	}

	public Float getTotalSessionLength() {
		return totalSessionLength;
	}

	public void setTotalSessionLength(Float totalSessionLength) {
		this.totalSessionLength = totalSessionLength;
	}

}
