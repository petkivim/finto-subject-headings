package com.pkrete.vocabulary.index;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.ResIterator;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.vocabulary.RDF;
import com.pkrete.vocabulary.api.IndexManager;
import com.pkrete.vocabulary.api.VocabularySearchService;
import com.pkrete.vocabulary.model.Term;
import com.pkrete.vocabulary.model.TermType;
import com.pkrete.vocabulary.model.Vocabulary;
import com.pkrete.vocabulary.util.Utils;

public class IndexManagerImpl implements IndexManager {

	private final static Logger logger = Logger
			.getLogger(IndexManagerImpl.class);
	private String conceptUri = "http://www.w3.org/2004/02/skos/core#Concept";
	private String prefLabelUri = "http://www.w3.org/2004/02/skos/core#prefLabel";
	private String altLabelUri = "http://www.w3.org/2004/02/skos/core#altLabel";
	private VocabularySearchService vocabularySearchService;

	public IndexManagerImpl() {
	}

	public IndexManagerImpl(VocabularySearchService vocabularySearchService) {
		this.vocabularySearchService = vocabularySearchService;
	}

	public void setConceptUri(String conceptUri) {
		this.conceptUri = conceptUri;
	}

	public void setPrefLabelUri(String prefLabelUri) {
		this.prefLabelUri = prefLabelUri;
	}

	public void setAltLabelUri(String altLabelUri) {
		this.altLabelUri = altLabelUri;
	}

	public void setVocabularySearchService(
			VocabularySearchService vocabularySearchService) {
		this.vocabularySearchService = vocabularySearchService;
	}

	public boolean build(String indexPath, String datastorePath,
			Vocabulary vocabulary) {
		// Init variables
		datastorePath = Utils.getDatastoreFilePath(datastorePath, vocabulary);
		String indexFile = Utils.getIndexFilePath(indexPath, vocabulary);
		String indexTempFile = Utils
				.getIndexTempFilePath(indexPath, vocabulary);

		logger.info("Start building datastore index.");
		logger.info("Vocabulary name : " + vocabulary.toString());
		logger.info("Datastore file : " + datastorePath);
		logger.info("Index file : " + indexFile);

		// Get datastore file as InputStream
		InputStream in = getResourceAsStream(datastorePath);

		// If InputStream is null -> exit
		if (in == null) {
			logger.warn("Building the index failed! Unable to access the datastore.");
			return false;
		}
		// Create an in-memory Jena Model
		Model model = ModelFactory.createDefaultModel();
		// Parse an InputStream assuming RDF in Turtle format
		model.read(in, null, "TURTLE");

		// New list for index entries
		List<Term> entries = new ArrayList<Term>();

		// Get all the concepts
		Resource datasetType = model.getResource(conceptUri);
		ResIterator datasets = model.listSubjectsWithProperty(RDF.type,
				datasetType);
		// Iterate through the concepts
		while (datasets.hasNext()) {
			Resource dataset = datasets.next();
			StmtIterator stmts = dataset.listProperties();
			while (stmts.hasNext()) {
				Statement statement = stmts.next();
				if (statement.getPredicate().toString()
						.equals(this.prefLabelUri)) {
					String termOrg = statement.getObject().toString();
					String[] term = termOrg.split("@");
					entries.add(new Term(dataset.toString(), term[0],
							TermType.PREFERRED));
				} else if (statement.getPredicate().toString()
						.equals(this.altLabelUri)) {
					String termOrg = statement.getObject().toString();
					String[] term = termOrg.split("@");
					entries.add(new Term(dataset.toString(), term[0],
							TermType.ALTERNATE));
				}
			}
		}
		// File presenting the index file
		File index = new File(indexFile);
		// Check if the index already exists
		if (!index.exists()) {
			// Index doesn't exist -> create the index file
			if (!writeToFile(entries, index)) {
				logger.error("Writing datastore index file failed!");
				return false;
			}
			logger.info("Inform registered vocabulary term searchers.");
			this.inform(vocabulary);
			return true;
		}
		// Index already exists -> write a temp file and replace the current
		// index file with the temp file
		File tempIndex = new File(indexTempFile);
		// Write results to a temp file
		if (writeToFile(entries, tempIndex)) {
			logger.debug("Trying to replace the old index file with the new one.");
			// Try to delete the current index
			if (!index.delete()) {
				logger.error("Failed to delete old datastore index file!");
				return false;
			}
			// Try to rename the temp file
			if (!tempIndex.renameTo(index)) {
				logger.error("Failed to rename new datastore index file!");
				return false;
			}
			logger.debug("The old file was succesfully replaced with the new one.");
			logger.info("Datastore index file was succesfully rebuilt. Inform registered vocabulary term searchers.");
			this.inform(vocabulary);
			return true;
		}
		logger.error("Writing datastore index file failed!");
		return false;
	}

	public void inform(Vocabulary vocabulary) {
		if (this.vocabularySearchService == null) {
			logger.warn("Vocabulary search service is null.");
			return;
		}
		this.vocabularySearchService.refresh(vocabulary);
		logger.info("Informed vocabulary search service.");
	}

	private boolean writeToFile(List<Term> list, File file) {
		Writer writer = null;
		logger.info("Start writing datastore index file.");
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file.getAbsolutePath()), "utf-8"));
			for (Term entry : list) {
				writer.write(entry.toString() + "\n");
			}
			logger.info("Wrote " + list.size() + " terms.");
		} catch (IOException ex) {
			logger.error("Failed to create datastore index file!");
			logger.error(ex);
			return false;
		}
		try {
			writer.close();
		} catch (Exception e) {
			logger.error("Failed to close datastore index file!");
			logger.error(e);
		}
		logger.info("Datastore index file succesfully written.");
		return true;
	}

	private InputStream getResourceAsStream(String filepath) {
		try {
			InputStream in = new FileInputStream(filepath);
			return in;
		} catch (Exception e) {
			logger.error("Failed to open datastore file!");
			logger.error(e);
			return null;
		}
	}
}
