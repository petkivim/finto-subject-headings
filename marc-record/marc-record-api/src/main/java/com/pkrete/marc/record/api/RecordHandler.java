package com.pkrete.marc.record.api;

import com.pkrete.marc.record.model.MarcRecord;
import com.pkrete.marc.record.model.MarcRecordCollection;

public interface RecordHandler {
	public MarcRecord process(MarcRecord record);

	public MarcRecordCollection process(MarcRecordCollection collection);
}
