package com.pkrete.marc.record.model;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "collection")
public class MarcRecordCollection implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "record")
	private List<MarcRecord> records;

	public List<MarcRecord> getRecords() {
		return records;
	}

	public MarcRecordCollection() {
	}

	public MarcRecordCollection(List<MarcRecord> records) {
		this.records = records;
	}

	public void setRecords(List<MarcRecord> records) {
		this.records = records;
	}

}
