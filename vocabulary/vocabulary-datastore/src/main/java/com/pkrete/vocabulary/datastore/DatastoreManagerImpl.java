package com.pkrete.vocabulary.datastore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.log4j.Logger;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.pkrete.vocabulary.api.DatastoreManager;
import com.pkrete.vocabulary.model.Vocabulary;
import com.pkrete.vocabulary.util.Utils;

public class DatastoreManagerImpl implements DatastoreManager {

	private final static Logger logger = Logger
			.getLogger(DatastoreManagerImpl.class);

	public boolean build(String datastorePath, String url, Vocabulary vocabulary) {
		// Init variables
		String datastoreFile = Utils.getDatastoreFilePath(datastorePath,
				vocabulary);
		String datastoreTempFile = Utils.getDatastoreTempFilePath(
				datastorePath, vocabulary);

		logger.info("Start building datastore.");
		logger.info("Vocabulary name : " + vocabulary.toString());
		logger.info("Source URL : " + url);
		logger.info("Datastore file : " + datastoreFile);

		// Creates an in-memory Jena Model
		Model model = ModelFactory.createDefaultModel();
		logger.info("Start reading data from source URL.");
		try {
			// Read data from source URL
			RDFDataMgr.read(model, url);
		} catch (Exception e) {
			logger.error("Reading data from source URL failed!");
			logger.error(e);
			logger.error("Building datastore failed!");
			return false;
		}
		logger.info("Reading data from source URL done.");
		// Build datastore path
		File file = new File(datastoreFile);
		// If datastore exists, it must be rebuilt
		if (file.exists()) {
			return update(model, datastoreFile, datastoreTempFile);
		}
		// Write data to file
		if (write(model, datastoreFile)) {
			logger.info("Datastore was succesfully built.");
			return true;
		}
		logger.error("Building datastore failed!");
		return false;
	}

	private boolean update(Model model, String datastoreFile,
			String datastoreTempFile) {
		// Write data to file
		if (!write(model, datastoreTempFile)) {
			logger.error("Rebuilding datastore failed!");
			return false;
		}
		if (rename(datastoreFile, datastoreTempFile)) {
			logger.info("Datastore was succesfully rebuilt.");
			return true;
		}
		logger.error("Rebuilding datastore failed!");
		return false;
	}

	private boolean write(Model model, String filePath) {
		OutputStream out = null;
		File file = new File(filePath);
		try {
			out = new FileOutputStream(file);
			RDFDataMgr.write(out, model, RDFFormat.TURTLE);
		} catch (Exception e) {
			logger.error("Writing data to datastore failed!");
			logger.error(e);
			return false;
		}
		try {
			out.close();
		} catch (Exception e) {
			logger.error("Failed to close the datastore file!");
			logger.error(e);
		}
		logger.info("Writing data to datastore succeeded.");
		return true;
	}

	private boolean rename(String datastoreFile, String datastoreTempFile) {
		logger.debug("Trying to replace the old datastore file with the new one.");
		File current = new File(datastoreFile);
		if (!current.delete()) {
			logger.error("Failed to remove the current datastore file!");
			return false;
		}
		File temp = new File(datastoreTempFile);
		if (!temp.renameTo(current)) {
			logger.error("Failed to rename the temp datastore file!");
			logger.error("Datastore file doesn't exist at the moment!");
			return false;
		}
		logger.debug("The old file was succesfully replaced with the new one.");
		return true;
	}
}
