package com.matthiasbaetens.gde.transforms;

import org.apache.beam.sdk.transforms.DoFn;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.matthiasbaetens.gde.messages.Log;

public class DetectLanguage extends DoFn<Log, Log>{

	@ProcessElement
	public void processElement(@Element Log log, OutputReceiver<Log> receiver) {	
		Log log_out = new Log(log);
	    Translate translate = TranslateOptions.getDefaultInstance().getService();
	    
	    Detection detectedLanguage = translate.detect(log.getText());	    
	    log_out.setTranslateConfidence(detectedLanguage.getConfidence());
	    log_out.setTranslateLanguage(detectedLanguage.getLanguage());
		
		receiver.output(log_out);
	}
}
