package com.pkrete.marc.record.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pkrete.marc.record.api.RecordHandler;
import com.pkrete.marc.record.model.DataField;
import com.pkrete.marc.record.model.MarcRecord;
import com.pkrete.marc.record.model.MarcRecordCollection;
import com.pkrete.marc.record.model.SubField;
import com.pkrete.vocabulary.api.VocabularySearchService;
import com.pkrete.vocabulary.model.Term;
import com.pkrete.vocabulary.model.Vocabulary;

public class RecordHandlerImpl implements RecordHandler {
	private static final Logger LOG = LoggerFactory
			.getLogger(RecordHandlerImpl.class);
	private VocabularySearchService vocabularySearchService;

	public void setVocabularySearchService(
			VocabularySearchService vocabularySearchService) {
		this.vocabularySearchService = vocabularySearchService;
	}

	public MarcRecord process(MarcRecord record) {
		LOG.info("Process single record.");
		for (DataField data : record.getDataFields("650", "2", "ysa")) {
			if (!data.hasSubField("0") && data.hasSubField("a")) {
				String termStr = data.getSubField("a").getValue();
				Term term = this.vocabularySearchService.search(termStr,
						Vocabulary.YSA);
				if (term != null) {
					LOG.info("Match found : \"" + termStr + "\" - \""
							+ term.getUri() + "\". Vocabulary: " + Vocabulary.YSA);
					data.addSubField(new SubField("0", term.getUri()));
				} else {
					LOG.info("No match found for term \"" + termStr + "\". Vocabulary: " + Vocabulary.YSA);
				}
			}
		}
		for (DataField data : record.getDataFields("650", "2", "allars")) {
			if (!data.hasSubField("0") && data.hasSubField("a")) {
				String termStr = data.getSubField("a").getValue();
				Term term = this.vocabularySearchService.search(termStr,
						Vocabulary.ALLARS);
				if (term != null) {
					LOG.info("Match found : \"" + termStr + "\" - \""
							+ term.getUri() + "\". Vocabulary: " + Vocabulary.ALLARS);
					data.addSubField(new SubField("0", term.getUri()));
				} else {
					LOG.info("No match found for term \"" + termStr + "\". Vocabulary: " + Vocabulary.ALLARS);
				}
			}
		}
		LOG.info("Processing done.");
		return record;

	}

	public MarcRecordCollection process(MarcRecordCollection collection) {
		LOG.info("Process " + collection.getRecords().size() + " records.");
		for (MarcRecord record : collection.getRecords()) {
			for (DataField data : record.getDataFields("650", "2", "ysa")) {
				if (!data.hasSubField("0") && data.hasSubField("a")) {
					String termStr = data.getSubField("a").getValue();
					Term term = this.vocabularySearchService.search(termStr,
							Vocabulary.YSA);
					if (term != null) {
						LOG.info("Match found : \"" + termStr + "\" - \""
								+ term.getUri() + "\". Vocabulary: " + Vocabulary.YSA);
						data.addSubField(new SubField("0", term.getUri()));
					} else {
						LOG.info("No match found for term \"" + termStr + "\". Vocabulary: " + Vocabulary.YSA);
					}
				}
			}
			for (DataField data : record.getDataFields("650", "2", "allars")) {
				if (!data.hasSubField("0") && data.hasSubField("a")) {
					String termStr = data.getSubField("a").getValue();
					Term term = this.vocabularySearchService.search(termStr,
							Vocabulary.ALLARS);
					if (term != null) {
						LOG.info("Match found : \"" + termStr + "\" - \""
								+ term.getUri() + "\". Vocabulary: " + Vocabulary.ALLARS);
						data.addSubField(new SubField("0", term.getUri()));
					} else {
						LOG.info("No match found for term \"" + termStr + "\". Vocabulary: " + Vocabulary.ALLARS);
					}
				}
			}
		}
		LOG.info("Processing done.");
		return collection;
	}
}
