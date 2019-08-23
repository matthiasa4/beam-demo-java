package com.matthiasbaetens.gde.messages;

import java.io.Serializable;

import org.apache.beam.sdk.transforms.windowing.IntervalWindow;

public class LanguageAggregate implements Serializable {

	IntervalWindow window;
	String language;
	Integer languageCount;

	public LanguageAggregate(IntervalWindow window, String language, Integer languageCount) {
		super();
		this.window = window;
		this.language = language;
		this.languageCount = languageCount;
	}

	public IntervalWindow getWindow() {
		return window;
	}

	public void setWindow(IntervalWindow window) {
		this.window = window;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getLanguageCount() {
		return languageCount;
	}

	public void setLanguageCount(Integer languageCount) {
		this.languageCount = languageCount;
	}
}