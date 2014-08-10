package com.pkrete.vocabulary.api;

import com.pkrete.vocabulary.model.Term;
import com.pkrete.vocabulary.model.Vocabulary;

public interface VocabularySearchService {
	public Term search(String term, Vocabulary vocabulary);

	public Term search(String term, String url, Vocabulary vocabulary);

	public void refresh();

	public boolean refresh(Vocabulary vocabulary);
}
