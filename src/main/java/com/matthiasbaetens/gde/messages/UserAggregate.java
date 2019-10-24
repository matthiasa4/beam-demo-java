package com.matthiasbaetens.gde.messages;

import java.io.Serializable;

import org.apache.beam.sdk.transforms.windowing.IntervalWindow;
import org.apache.beam.sdk.transforms.windowing.Window;

public class UserAggregate implements Serializable {
	String window;
	String userId;
	Integer numberOfLanguages;
	Integer numberOfSentences;
	double averageTimeBetween;
	long totalSessionLength;

	public UserAggregate(String window, String userId, Integer numberOfLanguages, Integer numberOfSentences,
			double averageTimeBetween, long totalSessionLength) {
		super();
		this.window = window;
		this.userId = userId;
		this.numberOfLanguages = numberOfLanguages;
		this.numberOfSentences = numberOfSentences;
		this.averageTimeBetween = averageTimeBetween;
		this.totalSessionLength = totalSessionLength;
	}

	public String getWindow() {
		return window;
	}

	public void setWindow(String window) {
		this.window = window;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public double getAverageTimeBetween() {
		return averageTimeBetween;
	}

	public void setAverageTimeBetween(double averageTimeBetween) {
		this.averageTimeBetween = averageTimeBetween;
	}

	public long getTotalSessionLength() {
		return totalSessionLength;
	}

	public void setTotalSessionLength(long totalSessionLength) {
		this.totalSessionLength = totalSessionLength;
	}

}
