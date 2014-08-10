package com.pkrete.vocabulary.api;

import com.pkrete.vocabulary.model.Vocabulary;

public interface IndexManager {

	public boolean build(String indexPath, String datastorePath, Vocabulary vocabulary);

	public void inform(Vocabulary vocabulary);
}
