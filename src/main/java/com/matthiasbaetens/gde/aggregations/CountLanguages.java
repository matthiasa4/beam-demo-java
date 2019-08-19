//package com.matthiasbaetens.gde.aggregations;
//
//import java.util.List;
//
//import org.apache.beam.examples.messages.LanguageAggregate;
//import org.apache.beam.examples.messages.Log;
//import org.apache.beam.sdk.transforms.DoFn;
//import org.apache.beam.sdk.transforms.DoFn.OutputReceiver;
//import org.apache.beam.sdk.transforms.windowing.Window;
//import org.apache.beam.sdk.values.KV;
//
//public class CountLanguages extends DoFn<KV<String, List<Log>>, OutputReceiver<LanguageAggregate>> {
//
//	@ProcessElement
//	public void processElement(@Element KV<String, List<Log>> languageGBK, OutputReceiver<LanguageAggregate> receiver) {
//		// Extract window
//		LanguageAggregate languageAggregate = new LanguageAggregate(
//				new Window(),
//				languageGBK.getKey(),
//				languageGBK.getValue().size());
//		
//		receiver.output(languageAggregate);
//
//	}
//}