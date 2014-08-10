package com.pkrete.vocabulary.search;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.pkrete.vocabulary.api.VocabularySearchService;
import com.pkrete.vocabulary.api.VocabularyTermSearch;
import com.pkrete.vocabulary.model.Term;
import com.pkrete.vocabulary.model.Vocabulary;

public class VocabularySearchServiceImpl implements VocabularySearchService {
	private final static Logger logger = Logger
			.getLogger(VocabularySearchServiceImpl.class);

	private Map<Vocabulary, VocabularyTermSearch> vocabularyTermSearchers;

	public VocabularySearchServiceImpl() {
		this.vocabularyTermSearchers = new HashMap<Vocabulary, VocabularyTermSearch>();
	}

	public VocabularySearchServiceImpl(
			Map<Vocabulary, VocabularyTermSearch> vocabularyTermSearchers) {
		this.vocabularyTermSearchers = vocabularyTermSearchers;
		logger.info("Registered " + this.vocabularyTermSearchers
				+ " vocabulary term searchers.");
	}

	public void setVocabularyTermSearchers(
			Map<Vocabulary, VocabularyTermSearch> vocabularyTermSearchers) {
		this.vocabularyTermSearchers = vocabularyTermSearchers;
		logger.info("Registered " + this.vocabularyTermSearchers
				+ " vocabulary term searchers.");
	}

	public Term search(String term, Vocabulary vocabulary) {
		if (this.vocabularyTermSearchers.get(vocabulary) == null) {
			logger.warn("Vocabulary \"" + vocabulary.toString()
					+ "\" is not currently loaded.");
			return null;
		}
		return this.vocabularyTermSearchers.get(vocabulary).search(term);
	}

	public Term search(String term, String url, Vocabulary vocabulary) {
		if (this.vocabularyTermSearchers.get(vocabulary) == null) {
			logger.warn("Vocabulary \"" + vocabulary.toString()
					+ "\" is not currently loaded.");
			return null;
		}
		Term temp = this.vocabularyTermSearchers.get(vocabulary).search(term);
		if (temp != null && temp.getUri().equals(url)) {
			return temp;
		}
		return null;
	}

	public void refresh() {
		for (Vocabulary vocabulary : this.vocabularyTermSearchers.keySet()) {
			this.vocabularyTermSearchers.get(vocabulary).refresh();
		}
	}

	public boolean refresh(Vocabulary vocabulary) {
		if (this.vocabularyTermSearchers.get(vocabulary) == null) {
			logger.info("No need to refresh vocabulary term searcher. The given vocabulary \""
					+ vocabulary.toString() + "\" is not currently loaded.");
			return false;
		}
		logger.info("Refresh vocabulary term searcher.");
		return this.vocabularyTermSearchers.get(vocabulary).refresh();
	}
}
