package com.matthiasbaetens.gde.io;

import org.apache.beam.sdk.transforms.DoFn;

import com.google.api.services.bigquery.model.TableRow;
import com.matthiasbaetens.gde.messages.LanguageAggregate;

public class LanguageToBigQuery extends DoFn<LanguageAggregate, TableRow> {
	@ProcessElement
	public void processElement(ProcessContext c) {
		LanguageAggregate languageAggregate = c.element();
		TableRow row = new TableRow()
				.set("window", languageAggregate.getWindow())
				.set("language", languageAggregate.getLanguage())
				.set("count", languageAggregate.getLanguageCount());
		c.output(row);
	}
}