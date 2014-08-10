package com.pkrete.marc.record.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "datafield")
public class DataField implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlAttribute(name = "tag")
	private String tag;
	@XmlAttribute(name = "ind1")
	private String ind1;
	@XmlAttribute(name = "ind2")
	private String ind2;
	@XmlElement(name = "subfield")
	private List<SubField> subFields;

	public DataField() {
		this.tag = "";
		this.ind1 = " ";
		this.ind2 = " ";
		this.subFields = new ArrayList<SubField>();
	}

	public DataField(String tag, String ind1, String ind2) {
		this.tag = tag;
		this.ind1 = ind1;
		this.ind2 = ind2;
		this.subFields = new ArrayList<SubField>();
	}

	public DataField(String tag, String ind1, String ind2,
			List<SubField> subFields) {
		this.tag = tag;
		this.ind1 = ind1;
		this.ind2 = ind2;
		this.subFields = subFields;
	}

	public String getInd1() {
		return ind1;
	}

	public void setInd1(String ind1) {
		this.ind1 = ind1;
	}

	public String getInd2() {
		return ind2;
	}

	public void setInd2(String ind2) {
		this.ind2 = ind2;
	}

	public List<SubField> getSubFields() {
		return subFields;
	}

	public void setSubFields(List<SubField> subFields) {
		this.subFields = subFields;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isTag(String tag) {
		if (this.tag.equals(tag)) {
			return true;
		}
		return false;
	}

	public void addSubField(SubField subField) {
		this.subFields.add(subField);
	}

	public SubField getSubField(String code) {
		for (SubField sub : this.subFields) {
			if (sub.getCode().equals(code)) {
				return sub;
			}
		}
		return null;
	}
	
	public List<SubField> getSubFields(String code) {
		List<SubField> temp = new ArrayList<SubField>();
		for (SubField sub : this.subFields) {
			if (sub.getCode().equals(code)) {
				temp.add(sub);
			}
		}
		return temp;
	}

	public boolean hasSubField(String code) {
		for (SubField sub : this.subFields) {
			if (sub.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}
}
