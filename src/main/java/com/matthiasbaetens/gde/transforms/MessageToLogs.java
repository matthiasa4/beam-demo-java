package com.matthiasbaetens.gde.transforms;

import org.apache.beam.sdk.io.gcp.pubsub.PubsubMessage;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.DoFn.ProcessContext;
import org.joda.time.DateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.matthiasbaetens.gde.messages.Log;
import com.matthiasbaetens.gde.utils.EpochDateTypeAdapter;

public class MessageToLogs extends DoFn<PubsubMessage, Log>{

	@ProcessElement
	public void processElement(ProcessContext c) {		
		Gson gson = new GsonBuilder().registerTypeAdapter(DateTime.class, new EpochDateTypeAdapter()).create();
		Log log = gson.fromJson(new String(c.element().getPayload()), Log.class);
		log.setTimestamp(new DateTime(c.timestamp().getMillis()));
				
		c.output(log);
	}
}
