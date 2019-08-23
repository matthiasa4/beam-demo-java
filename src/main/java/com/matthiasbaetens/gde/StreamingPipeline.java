package com.matthiasbaetens.gde;

import java.util.ArrayList;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO.Write.CreateDisposition;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO.Write.WriteDisposition;
import org.apache.beam.sdk.io.gcp.pubsub.PubsubIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.GroupByKey;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.windowing.FixedWindows;
import org.apache.beam.sdk.transforms.windowing.Sessions;
import org.apache.beam.sdk.transforms.windowing.Window;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.PCollection;
import org.joda.time.Duration;

import com.google.api.services.bigquery.model.TableFieldSchema;
import com.google.api.services.bigquery.model.TableReference;
import com.google.api.services.bigquery.model.TableSchema;
import com.google.common.collect.ImmutableList;
import com.matthiasbaetens.gde.aggregations.CountLanguages;
import com.matthiasbaetens.gde.aggregations.CountUsers;
import com.matthiasbaetens.gde.config.StreamingPipelineOptions;
import com.matthiasbaetens.gde.io.LanguageToBigQuery;
import com.matthiasbaetens.gde.io.LanguageToPubSub;
import com.matthiasbaetens.gde.io.LogToBigQuery;
import com.matthiasbaetens.gde.io.UserToBigQuery;
import com.matthiasbaetens.gde.messages.LanguageAggregate;
import com.matthiasbaetens.gde.messages.Log;
import com.matthiasbaetens.gde.transforms.DetectLanguage;
import com.matthiasbaetens.gde.transforms.ExtractLanguageTuple;
import com.matthiasbaetens.gde.transforms.ExtractUserTuple;
import com.matthiasbaetens.gde.transforms.MessageToLogs;

public class StreamingPipeline {
	// TODO:
	// BigQuery checks

	public static void main(String[] args) {
		StreamingPipelineOptions options = PipelineOptionsFactory.fromArgs(args).withValidation()
				.as(StreamingPipelineOptions.class);

		Pipeline p = Pipeline.create(options);
		String project = "matthias-sandbox";
		String dataset = "devfest_demo";
		
		String pubSub_output = "projects/gde-front-end/topics/languages";

		// Main pipeline: read in Logs, write them to BigQuery
		String log_table = "logs_java";

		TableReference tableSpec = new TableReference().setProjectId(project).setDatasetId(dataset)
				.setTableId(log_table);

		PCollection<Log> logs = p
				.apply("Read ", PubsubIO.readStrings().fromSubscription(options.getInputSubscription()))
				.apply("Parse messages to Logs", ParDo.of(new MessageToLogs()))
				.apply("Detect language", ParDo.of(new DetectLanguage()));
		
		TableSchema tableSchema =
				new TableSchema()
					.setFields(
							ImmutableList.of(
									new TableFieldSchema()
										.setName("timestamp")
										.setType("FLOAT")
										.setMode("NULLABLE"),
									new TableFieldSchema()
										.setName("user_id")
										.setType("STRING")
										.setMode("NULLABLE"),
									new TableFieldSchema()
										.setName("text")
										.setType("STRING")
										.setMode("NULLABLE"),
									new TableFieldSchema()
										.setName("language")
										.setType("RECORD")
										.setFields(
												new ArrayList<TableFieldSchema>() {
													{
														add(new TableFieldSchema()
																.setName("translate_language")
																.setType("STRING")
																.setMode("NULLABLE"));
														add(new TableFieldSchema()
																.setName("translate_confidence")
																.setType("STRING")
																.setMode("NULLABLE"));
													}
												})									
											));

		logs
		.apply("Convert Log to BigQuery records", ParDo.of(new LogToBigQuery()))
		.apply("Write logs to BigQuery",
				BigQueryIO.writeTableRows()
					.to(tableSpec)
					.withSchema(tableSchema)
					.withCreateDisposition(CreateDisposition.CREATE_IF_NEEDED)
					.withWriteDisposition(WriteDisposition.WRITE_APPEND));

		// Calculate aggregate per language, write to BigQuery
		String language_aggreate_table = "languages_java";
		
		TableReference tableSpec_language_aggregate = new TableReference()
				.setProjectId(project)
				.setDatasetId(dataset)
				.setTableId(language_aggreate_table);

		PCollection<LanguageAggregate> languages = logs
				.apply("Extract language tuple", MapElements.via(new ExtractLanguageTuple()))
				.apply("Assign Fixed Windows", Window.<KV<String, Log>>into(FixedWindows.of(Duration.standardSeconds(5))))
				.apply("GroupByKey Languages", GroupByKey.<String, Log>create())
				.apply("Count Languages", ParDo.of(new CountLanguages()));

		TableSchema tableSchema_language_aggregate =
				new TableSchema()
					.setFields(
							ImmutableList.of(
									new TableFieldSchema()
										.setName("window")
										.setType("STRING")
										.setMode("NULLABLE"),
									new TableFieldSchema()
										.setName("language")
										.setType("STRING")
										.setMode("NULLABLE"),
									new TableFieldSchema()
										.setName("count")
										.setType("INTEGER")
										.setMode("NULLABLE")));
		
		languages
				.apply("Convert Language aggregate to BigQuery records", ParDo.of(new LanguageToBigQuery()))
				.apply("Write Language aggregate to BigQuery", BigQueryIO.writeTableRows()
						.to(tableSpec_language_aggregate)
						.withSchema(tableSchema_language_aggregate)
						.withCreateDisposition(CreateDisposition.CREATE_IF_NEEDED)
						.withWriteDisposition(WriteDisposition.WRITE_APPEND));
		languages
				.apply("Convert Language aggregate to PubSub message", ParDo.of(new LanguageToPubSub()))
				.apply("Write Language aggregate to PubSub", PubsubIO.writeStrings().to(pubSub_output));

		// Calculate aggregates per user, write to BigQuery
		String user_aggregate_table = "users_java";
		
		TableReference tableSpec_user_aggregate = new TableReference().setProjectId(project).setDatasetId(dataset)
				.setTableId(user_aggregate_table);
		
		TableSchema tableSchema_user_aggregate =
				new TableSchema()
					.setFields(
							ImmutableList.of(
									new TableFieldSchema()
										.setName("user_id")
										.setType("STRING")
										.setMode("NULLABLE"),
									new TableFieldSchema()
										.setName("window")
										.setType("STRING")
										.setMode("NULLABLE"),
									new TableFieldSchema()
										.setName("number_of_sentences")
										.setType("INTEGER")
										.setMode("NULLABLE"),
									new TableFieldSchema()
										.setName("number_of_languages")
										.setType("INTEGER")
										.setMode("NULLABLE"),
									new TableFieldSchema()
										.setName("average_time_between")
										.setType("FLOAT")
										.setMode("NULLABLE"),
									new TableFieldSchema()
										.setName("total_session_length")
										.setType("FLOAT")
										.setMode("NULLABLE")));
		
		logs.apply("Extract user tuple", MapElements.via(new ExtractUserTuple()))
				.apply("Assign sessions", Window.<KV<String, Log>>into(Sessions.withGapDuration(Duration.standardSeconds(30))))
				.apply("GroupByKey Users", GroupByKey.<String, Log>create())
				.apply("Count Users", ParDo.of(new CountUsers()))
				.apply("Convert user aggregate to BigQuery records", ParDo.of(new UserToBigQuery()))
				.apply("Write user aggregate to BigQuery", BigQueryIO.writeTableRows()			
						.to(tableSpec_user_aggregate)
						.withSchema(tableSchema_user_aggregate)
						.withCreateDisposition(CreateDisposition.CREATE_IF_NEEDED)
						.withWriteDisposition(WriteDisposition.WRITE_APPEND));

		p.run().waitUntilFinish();
	}
}
