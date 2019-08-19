package com.matthiasbaetens.gde.utils;

import java.io.IOException;

import org.joda.time.DateTime;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class EpochDateTypeAdapter extends TypeAdapter<DateTime> {
	@Override
	public void write(JsonWriter out, DateTime value) throws IOException {
		if (value == null)
			out.nullValue();
		else
			out.value(value.getMillis() / 1000);
	}

	@Override
	public DateTime read(JsonReader in) throws IOException {
		if (in != null)
			return new DateTime(in.nextLong() * 1000);
		else
			return null;
	}
}
