package com.matthiasbaetens.gde.io;

import org.apache.beam.sdk.transforms.DoFn;

import com.google.gson.JsonObject;
import com.matthiasbaetens.gde.messages.LanguageAggregate;

public class LanguageToPubSub extends DoFn<LanguageAggregate, String> {
	@ProcessElement
	public void processElement(@Element LanguageAggregate languageAggregate, OutputReceiver<String> receiver) {	
		JsonObject json = new JsonObject();
		json.addProperty("window", languageAggregate.getWindow());
		json.addProperty("language", languageAggregate.getLanguage());
		json.addProperty("count", languageAggregate.getLanguageCount());
		
		receiver.output(json.toString());
	}

}
