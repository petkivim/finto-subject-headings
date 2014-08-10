package com.pkrete.vocabulary.util;

import com.pkrete.vocabulary.model.Vocabulary;

public class Utils {

    public static String getDatastoreFilePath(String path, Vocabulary vocabulary) {
        return path + vocabulary.toString() + "." + DatastoreConstants.DATASTORE_FILE;
    }

    public static String getDatastoreTempFilePath(String path, Vocabulary vocabulary) {
        return path + vocabulary.toString() + "." + DatastoreConstants.DATASTORE_TEMP_FILE;
    }

    public static String getIndexFilePath(String path, Vocabulary vocabulary) {
        return path + vocabulary.toString() + "." + DatastoreConstants.INDEX_FILE;
    }

    public static String getIndexTempFilePath(String path, Vocabulary vocabulary) {
        return path + vocabulary.toString() + "." + DatastoreConstants.INDEX_TEMP_FILE;
    }
}
