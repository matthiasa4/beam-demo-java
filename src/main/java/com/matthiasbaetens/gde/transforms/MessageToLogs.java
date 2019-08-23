package com.matthiasbaetens.gde.transforms;

import org.apache.beam.sdk.transforms.DoFn;
import org.joda.time.DateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matthiasbaetens.gde.messages.Log;
import com.matthiasbaetens.gde.utils.EpochDateTypeAdapter;

public class MessageToLogs extends DoFn<String, Log>{

	@ProcessElement
	public void processElement(@Element String message, OutputReceiver<Log> receiver) {
		Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new EpochDateTypeAdapter()).create();
		Log log = gson.fromJson(message, Log.class);

		receiver.output(log);
	}
}
