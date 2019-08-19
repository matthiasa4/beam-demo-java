//package com.matthiasbaetens.gde.aggregations;
//
//import java.util.List;
//
//import org.apache.beam.sdk.transforms.DoFn;
//import org.apache.beam.sdk.transforms.windowing.Window;
//import org.apache.beam.sdk.values.KV;
//
//import com.matthiasbaetens.gde.messages.Log;
//import com.matthiasbaetens.gde.messages.UserAggregate;
//
//public class CountUsers extends DoFn<KV<String, List<Log>>, UserAggregate> {
//
//	@ProcessElement
//	public void processElement(ProcessContext c) {
//		// extract window
//		KV<String, List<Log>> in = c.element();
//		UserAggregate userAggregate = new UserAggregate(
//				new Window(), 
//				in.getKey(), 
//				in.getValue().size());
//		
//		c.output(userAggregate);		
//	}
//}
