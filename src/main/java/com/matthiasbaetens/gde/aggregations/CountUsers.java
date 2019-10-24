package com.matthiasbaetens.gde.aggregations;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.DoFn.ProcessContext;
import org.apache.beam.sdk.transforms.windowing.BoundedWindow;
import org.apache.beam.sdk.transforms.windowing.IntervalWindow;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.vendor.guava.v20_0.com.google.common.collect.Lists;

import com.matthiasbaetens.gde.messages.Log;
import com.matthiasbaetens.gde.messages.UserAggregate;

public class CountUsers extends DoFn<KV<String, Iterable<Log>>, UserAggregate> {

	@ProcessElement
	public void processElement(ProcessContext c, BoundedWindow window) {
		KV<String, Iterable<Log>> in = c.element();

		List<Log> logs = Lists.newArrayList(in.getValue());
		Collections.sort(logs);
		HashSet<String> languages = new HashSet<String>();
		double timeBetween = 0;
		for (int i = 0; i < logs.size(); i++) {
			Log currentLog = logs.get(i);
			languages.add(currentLog.getTranslateLanguage());
			if (i > 0) {
				timeBetween = timeBetween + currentLog.getTimestamp().getMillis()
						- logs.get(i - 1).getTimestamp().getMillis();
			}
		}

		UserAggregate userAggregate = new UserAggregate(window.toString(), in.getKey(), languages.size(), logs.size(),
				timeBetween / logs.size(),
				logs.get(logs.size() - 1).getTimestamp().getMillis() - logs.get(0).getTimestamp().getMillis());

		c.output(userAggregate);
	}
}
