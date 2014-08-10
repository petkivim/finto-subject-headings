package com.pkrete.vocabulary.search;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.pkrete.vocabulary.api.VocabularyTermSearch;
import com.pkrete.vocabulary.model.Term;
import com.pkrete.vocabulary.model.TermType;
import com.pkrete.vocabulary.model.Vocabulary;
import com.pkrete.vocabulary.util.Utils;

public class CacheIndexSearch implements VocabularyTermSearch {
	private final static Logger logger = Logger
			.getLogger(CacheIndexSearch.class);
	private String indexPath;
	private Map<String, Term> cache;
	private Vocabulary vocabulary;

	public CacheIndexSearch(String indexPath, Vocabulary vocabulary) {
		this.indexPath = Utils.getIndexFilePath(indexPath, vocabulary);
		this.vocabulary = vocabulary;
		this.loadIndex(this.indexPath);
	}

	public Term search(String term) {
		if (this.cache == null) {
			logger.warn("Index cache is null. Search failed!");
			return null;
		}
		return this.cache.get(term);
	}

	public Term search(String term, String url) {
		if (this.cache == null) {
			logger.warn("Index cache is null. Search failed!");
			return null;
		}
		Term temp = this.cache.get(term);
		if (temp != null && temp.getUri().equals(url)) {
			return temp;
		}
		return null;
	}

	public boolean refresh() {
		return this.refresh(this.vocabulary);
	}

	public boolean refresh(Vocabulary vocabulary) {
		if (this.vocabulary == vocabulary) {
			logger.info("Refresh index cache.");
			return this.loadIndex(indexPath);
		}
		logger.info("No need to refresh index cache (\""
				+ this.vocabulary.toString() + "\" != \""
				+ vocabulary.toString() + "\").");
		return false;
	}

	private boolean loadIndex(String indexPath) {
		logger.info("Start loading index into cache.");
		logger.info("Vocabulary name : " + vocabulary.toString());
		logger.info("Index file : " + indexPath);

		if (!new File(indexPath).exists()) {
			logger.warn("The given index file doesn't exist.");
			return false;
		}
		Map<String, Term> tempCache = new HashMap<String, Term>();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new FileInputStream(indexPath), "UTF-8");
			while (scanner.hasNextLine()) {
				// One line containts one term
				// [0] = URI, [1] = term, [2] = type
				String[] term = scanner.nextLine().split("\\t");
				// Get term type
				TermType type = term[2].equals(TermType.ALTERNATE.toString()) ? TermType.ALTERNATE
						: TermType.PREFERRED;
				// Add term to temp cache
				tempCache.put(term[0], new Term(term[1], term[0], type));
				// Some terms may have the same alternate term. In this case
				// it's impossible to know which is the referred term.
				// For example: lukutekniikka, dekkarit, vaatetus
			}
		} catch (Exception e) {
			logger.error("Loading index into cache failed.");
			logger.error(e);
			return false;
		} finally {
			scanner.close();
		}
		this.cache = tempCache;
		logger.info("Index was succesfully loaded into cache.");
		logger.info("Index cache contains " + this.cache.size() + " terms.");
		return true;
	}
}