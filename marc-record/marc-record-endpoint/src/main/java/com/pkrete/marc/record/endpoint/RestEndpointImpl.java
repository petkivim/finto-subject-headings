package com.pkrete.marc.record.endpoint;

import com.pkrete.marc.record.api.RecordHandler;
import com.pkrete.marc.record.api.RestEndpoint;
import com.pkrete.marc.record.model.MarcRecord;
import com.pkrete.marc.record.model.MarcRecordCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestEndpointImpl implements RestEndpoint {
	private static final Logger LOG = LoggerFactory.getLogger(RestEndpointImpl.class);
	private RecordHandler handler;

	public void setHandler(RecordHandler handler) {
		this.handler = handler;
	}
	
	public MarcRecord doRecord(MarcRecord record) {
		LOG.info("Received single record.");
		return this.handler.process(record);
	}
	
	public MarcRecordCollection doRecords(MarcRecordCollection collection) {
		LOG.info("Received " + collection.getRecords().size() + " records.");
		return this.handler.process(collection);
	}
}
