package com.pkrete.vocabulary.maintenance;

import org.apache.log4j.Logger;

import com.pkrete.vocabulary.api.DataManager;
import com.pkrete.vocabulary.api.DatastoreManager;
import com.pkrete.vocabulary.api.IndexManager;
import com.pkrete.vocabulary.model.Vocabulary;

public class DataManagerImpl implements DataManager {

    private final static Logger logger = Logger.getLogger(DataManagerImpl.class);
    private DatastoreManager datastore;
    private IndexManager index;

    public DataManagerImpl() {
    }

    public DataManagerImpl(DatastoreManager datastore, IndexManager index) {
        this.datastore = datastore;
        this.index = index;
    }

    public void setDatastore(DatastoreManager datastore) {
        this.datastore = datastore;
    }

    public void setIndex(IndexManager index) {
        this.index = index;
    }

    public boolean build(String datastorePath, String datastoreSourceUrl, String indexPath, Vocabulary vocabulary) {
        logger.info("Start building datastore and index.");
        if (!datastore.build(datastorePath, datastoreSourceUrl, vocabulary)) {
            logger.error("Building datastore failed! Skip indexing.");
            return false;
        }
        if (!index.build(indexPath, datastorePath, vocabulary)) {
            logger.error("Building index failed! System may be without working index or index is outdated.");
            return false;
        }
        logger.info("Datastore and index are up-to-date.");
        return true;
    }
}

