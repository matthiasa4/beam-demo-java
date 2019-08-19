package com.matthiasbaetens.gde;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;

import com.matthiasbaetens.gde.config.StreamingPipelineOptions;
import com.matthiasbaetens.gde.messages.Log;
import com.matthiasbaetens.gde.transforms.DetectLanguage;
import com.matthiasbaetens.gde.transforms.MessageToLogs;

public class StreamingPipeline {
	// TODO:
	// BigQuery checks

	public static void main(String[] args) {
		StreamingPipelineOptions options = PipelineOptionsFactory.fromArgs(args).withValidation()
				.as(StreamingPipelineOptions.class);

		Pipeline p = Pipeline.create(options);
		// Main pipeline: read in Logs, write them to BigQuery
		String log_table = "logs";
		PCollection<Log> logs = p
				.apply("Read ", PubsubIO.readStrings().fromSubscription(options.getInputSubscription()))
				.apply("Parse messages to Logs", ParDo.of(new MessageToLogs()))
				.apply("Detect language", ParDo.of(new DetectLanguage()))
		;
//
//		logs.apply("Convert Log to BigQuery records", ParDo.of(new LogToBigQuery())).apply("Write logs to BigQuery",
//				BigQueryIO.writeTableRows());

//		// Calculate aggregate per language, write to BigQuery
//		String language_aggreate_table = "languages";
//
//		PCollection<String> languages = logs
//				.apply("Extract language tuple", MapElements.via(new ExtractLanguageTuple()))
//				.apply("Assign Fixed Windows", Window.into(FixedWindows.of(Duration.standardSeconds(60))))
//				.apply("GroupByKey Languages", GroupByKey.<String, Log>create())
//				.apply("Count Languages", ParDo.of(new CountLanguages()));
//
//		languages.apply("Convert Language aggregate to BigQuery records", ParDo.of(new LanguageToBigQuery()))
//				.apply("Write Language aggregate to BigQuery", BigQueryIO.writeTableRows())
//				.apply("Convert Language aggregate to PubSub message", ParDo.of(new LanguageToPubSub()))
//				.apply("Encode", ParDo.of(new EncodeLanguage()))
//				.apply("Write Language aggregate to PubSub", PubsubIO.writeStrings());
//
//		// Calculate aggregates per user, write to BigQuery
//		String user_aggregate_table = "user";
//		logs.apply("Extract user tuple", MapElements.via(new ExtractUserTuple()))
//				.apply("Assign sessions", Window.into(Sessions.withGapDuration(Duration.standardSeconds(30))))
//				.apply("GroupByKey Users", GroupByKey.<String, Log>create())
//				.apply("Count Users", ParDo.of(new CountUsers()))
//				.apply("Conver user aggregate to BigQuery records", ParDo.of(new UserToBigQuery()))
//				.apply("Write user aggregate to BigQuery", BigQueryIO.writeTableRows());

		p.run().waitUntilFinish();
	}
}
