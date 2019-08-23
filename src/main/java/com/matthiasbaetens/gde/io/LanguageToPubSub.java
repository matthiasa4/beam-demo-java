package com.matthiasbaetens.gde.io;

import org.apache.beam.sdk.transforms.DoFn;

import com.google.gson.JsonObject;
import com.matthiasbaetens.gde.messages.LanguageAggregate;

public class LanguageToPubSub extends DoFn<LanguageAggregate, String> {
	@ProcessElement
	public void processElement(@Element LanguageAggregate languageAggregate, OutputReceiver<String> receiver) {	
		JsonObject json = new JsonObject();
		json.addProperty("window_start", Long.toString(languageAggregate.getWindow().start().getMillis()));
		json.addProperty("window_end", Long.toString(languageAggregate.getWindow().end().getMillis()));
		json.addProperty("language", languageAggregate.getLanguage());
		json.addProperty("count", languageAggregate.getLanguageCount());

		System.out.println(languageAggregate.getWindow().toString());	
		System.out.println(languageAggregate.getWindow().maxTimestamp());
		
		receiver.output(json.toString());
	}

}
