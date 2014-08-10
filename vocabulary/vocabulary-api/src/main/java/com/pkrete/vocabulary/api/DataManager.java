package com.pkrete.vocabulary.api;

import com.pkrete.vocabulary.model.Vocabulary;

public interface DataManager {

    public boolean build(String datastorePath, String datastoreSourceUrl, String indexPath, Vocabulary vocabulary);
}
