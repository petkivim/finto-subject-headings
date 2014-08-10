package com.pkrete.vocabulary.api;

import com.pkrete.vocabulary.model.Vocabulary;

public interface DatastoreManager {
	   public boolean build(String path, String url, Vocabulary vocabulary); 
}