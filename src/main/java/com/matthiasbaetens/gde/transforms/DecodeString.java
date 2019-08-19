package com.matthiasbaetens.gde.transforms;

import org.apache.beam.sdk.transforms.DoFn;

import com.matthiasbaetens.gde.messages.Log;

public class DecodeString extends DoFn<String, String> {

	@ProcessElement
	public void processElement(@Element String string, OutputReceiver<String> receiver) {
		System.out.println(string);
	}

}
