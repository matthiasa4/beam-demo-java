package com.matthiasbaetens.gde.io;

import org.apache.beam.sdk.transforms.DoFn;

import com.google.api.services.bigquery.model.TableRow;
import com.matthiasbaetens.gde.messages.UserAggregate;

public class UserToBigQuery extends DoFn<UserAggregate, TableRow> {
	@ProcessElement
	public void processElement(ProcessContext c) {
		UserAggregate userAggregate = c.element();
		TableRow row = new TableRow()
				.set("user_id", userAggregate.getUserId())
				.set("window",userAggregate.getWindow().toString())
				.set("number_of_sentences", userAggregate.getNumberOfSentences())
				.set("number_of_languages", userAggregate.getNumberOfLanguages())
				.set("average_time_between", userAggregate.getAverageTimeBetween())
				.set("total_session_length", userAggregate.getTotalSessionLength());
		c.output(row);
	}
}