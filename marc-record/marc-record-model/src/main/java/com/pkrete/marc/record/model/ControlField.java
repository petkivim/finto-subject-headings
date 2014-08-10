package com.pkrete.marc.record.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "controlfield")
public class ControlField implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlAttribute(name = "tag")
	private String tag;
	@XmlValue
	private String value;

	public ControlField() {
		this.tag = "";
		this.value = "";
	}

	public ControlField(String tag, String value) {
		this.tag = tag;
		this.value = value;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isTag(String tag) {
		if (this.tag.equals(tag)) {
			return true;
		}
		return false;
	}
}
