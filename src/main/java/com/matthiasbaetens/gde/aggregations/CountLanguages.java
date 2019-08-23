package com.matthiasbaetens.gde.aggregations;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.windowing.IntervalWindow;
import org.apache.beam.sdk.values.KV;
import org.apache.commons.collections4.IterableUtils;

import com.matthiasbaetens.gde.messages.LanguageAggregate;
import com.matthiasbaetens.gde.messages.Log;

public class CountLanguages extends DoFn<KV<String, Iterable<Log>>, LanguageAggregate> {

	@ProcessElement
	public void processElement(@Element KV<String, Iterable<Log>> languageGBK, IntervalWindow window, OutputReceiver<LanguageAggregate> receiver) {
		// Extract window
		LanguageAggregate languageAggregate = new LanguageAggregate(
				window,
				languageGBK.getKey(),
				IterableUtils.size(languageGBK.getValue()));
		
		System.out.println(window);
		System.out.println(window.toString());
		
		receiver.output(languageAggregate);
	}
}