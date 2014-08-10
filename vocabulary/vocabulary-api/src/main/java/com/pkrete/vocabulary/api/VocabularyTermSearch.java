package com.pkrete.vocabulary.api;

import com.pkrete.vocabulary.model.Term;
import com.pkrete.vocabulary.model.Vocabulary;

public interface VocabularyTermSearch {

	public Term search(String term);

	public Term search(String term, String url);

	public boolean refresh();

	public boolean refresh(Vocabulary vocabulary);
}
