package com.matthiasbaetens.gde.io;

import org.apache.beam.sdk.transforms.DoFn;

import com.google.api.services.bigquery.model.TableRow;
import com.matthiasbaetens.gde.messages.Log;

public class LogToBigQuery extends DoFn<Log, TableRow> {
	@ProcessElement
	public void processElement(ProcessContext c) {
		Log log = c.element();
		TableRow row = new TableRow()
				.set("timestamp", log.getTimestamp())
				.set("text", log.getText())
				.set("user_id", log.getUserId())
				.set("translate_language", log.getTranslateLanguage())
				.set("translate_confidence", log.getTranslateConfidence());
		c.output(row);
	}
}