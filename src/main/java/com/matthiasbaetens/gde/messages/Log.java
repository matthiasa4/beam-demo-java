package com.matthiasbaetens.gde.messages;

import java.io.Serializable;

import org.joda.time.DateTime;

public class Log implements Serializable, Comparable<Log> {
	String text;
	String userId;
	String translateLanguage;
	Float translateConfidence;
	DateTime timestamp;

	public Log(Log original) {
		this.text = original.text;
		this.userId = original.userId;
		this.translateLanguage = original.translateLanguage;
		this.translateConfidence = original.translateConfidence;
		this.timestamp = original.timestamp;
	}

	public DateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(DateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTranslateLanguage() {
		return translateLanguage;
	}

	public void setTranslateLanguage(String translateLanguage) {
		this.translateLanguage = translateLanguage;
	}

	public Float getTranslateConfidence() {
		return translateConfidence;
	}

	public void setTranslateConfidence(Float translateConfidence) {
		this.translateConfidence = translateConfidence;
	}

	public int compareTo(Log other) {
		return new Long(this.getTimestamp().getMillis()).compareTo(other.getTimestamp().getMillis());
	}
}