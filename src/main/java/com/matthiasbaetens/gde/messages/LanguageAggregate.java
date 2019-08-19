package com.matthiasbaetens.gde.messages;

import org.apache.beam.sdk.transforms.windowing.Window;

public class LanguageAggregate {

	Window window;
	String language;
	Integer languageCount;

	public LanguageAggregate(Window window, String language, Integer languageCount) {
		super();
		this.window = window;
		this.language = language;
		this.languageCount = languageCount;
	}

	public Window getWindow() {
		return window;
	}

	public void setWindow(Window window) {
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