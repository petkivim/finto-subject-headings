package com.pkrete.vocabulary.maintenance;

import java.util.Map;

import org.apache.log4j.Logger;

import com.pkrete.vocabulary.api.DataManager;
import com.pkrete.vocabulary.api.MaintenanceManager;
import com.pkrete.vocabulary.model.Vocabulary;

public class MaintenanceManagerImpl implements MaintenanceManager {

    private final static Logger logger = Logger.getLogger(MaintenanceManagerImpl.class);
    private Map<Vocabulary, String> vocabularies;
    private DataManager dataManager;
    private String datastorePath;
    private String indexPath;

    public void setVocabularies(Map<Vocabulary, String> vocabularies) {
        this.vocabularies = vocabularies;
    }

    public void setDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public void setDatastorePath(String datastorePath) {
        this.datastorePath = datastorePath;
    }

    public void setIndexPath(String indexPath) {
        this.indexPath = indexPath;
    }

    public void doMaintenance() {
        logger.info("Start maintenance operation.");
        logger.info("Operation covers " + vocabularies.size() + " vocabularies.");
        logger.info("Datastore path : " + datastorePath);
        logger.info("Index path : " + indexPath);
        for (Vocabulary vocabulary : this.vocabularies.keySet()) {
            this.dataManager.build(datastorePath, this.vocabularies.get(vocabulary), indexPath, vocabulary);
        }
        logger.info("Maintenance operation completed.");
    }
}
