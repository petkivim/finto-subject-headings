package com.pkrete.marc.record.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "subfield")
public class SubField implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlAttribute(name = "code")
	private String code;
	@XmlValue
	private String value;

	public SubField() {
		this.code = "";
		this.value = "";
	}

	public SubField(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean contains(String code, String value) {
		if (this.code.equals(code) && this.value.equals(value)) {
			return true;
		}
		return false;
	}

	public boolean isCode(String code) {
		if (this.code.equals(code)) {
			return true;
		}
		return false;
	}
}
