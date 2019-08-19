package com.matthiasbaetens.gde.transforms;

import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;

import com.matthiasbaetens.gde.messages.Log;

public class ExtractLanguageTuple extends SimpleFunction<Log, KV<String, Log>> {

	@Override
	public KV<String, Log> apply(Log input) {
		KV<String, Log> output = KV.of(input.getTranslateLanguage(), input);
		return output;
	}

}
