package com.pkrete.vocabulary.model;

import java.io.Serializable;

public class Term implements Serializable {
	private static final long serialVersionUID = 1L;
    private String uri;
    private String term;
    private TermType type;

    public Term() {
    }

    public Term(String uri, String term, TermType type) {
        this.uri = uri;
        this.term = term;
        this.type = type;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public TermType getType() {
        return type;
    }

    public void setType(TermType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return term + "\t" + uri + "\t" + type;
    }
}

