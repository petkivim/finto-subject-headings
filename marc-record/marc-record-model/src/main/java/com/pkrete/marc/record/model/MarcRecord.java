package com.pkrete.marc.record.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "record")
public class MarcRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	@XmlElement(name = "leader")
	private String leader;
	@XmlElement(name = "controlfield")
	private List<ControlField> controlFields;
	@XmlElement(name = "datafield")
	private List<DataField> dataFields;

	public MarcRecord() {
		this.leader = "";
		this.controlFields = new ArrayList<ControlField>();
		this.dataFields = new ArrayList<DataField>();
	}

	public List<ControlField> getControlFields() {
		return controlFields;
	}

	public void setControlFields(List<ControlField> controlFields) {
		this.controlFields = controlFields;
	}

	public List<DataField> getDataFields() {
		return dataFields;
	}

	public void setDataFields(List<DataField> dataFields) {
		this.dataFields = dataFields;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	public void addControlField(ControlField controlField) {
		this.controlFields.add(controlField);
	}

	public void addDataField(DataField dataField) {
		this.dataFields.add(dataField);
	}

	public List<DataField> getDataFields(String tag) {
		List<DataField> temp = new ArrayList<DataField>();
		for (DataField data : this.dataFields) {
			if (data.getTag().equals(tag)) {
				temp.add(data);
			}
		}
		return temp;
	}

	public List<DataField> getDataFields(String tag, String subFieldCode,
			String subFieldValue) {
		List<DataField> temp = new ArrayList<DataField>();
		for (DataField data : this.getDataFields(tag)) {
			for (SubField sub : data.getSubFields()) {
				if (sub.contains(subFieldCode, subFieldValue)) {
					temp.add(data);
					break;
				}
			}
		}
		return temp;
	}

	public List<ControlField> getControlFields(String tag) {
		List<ControlField> temp = new ArrayList<ControlField>();
		for (ControlField control : this.controlFields) {
			if (control.getTag().equals(tag)) {
				temp.add(control);
			}
		}
		return temp;
	}
}
