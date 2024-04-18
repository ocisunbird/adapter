package com.uci.adapter.netcore.whatsapp.outbound.nic;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@XmlRootElement
public class GUID {

	@JsonProperty("SUBMITDATE")
	public String submitDate;
	@JsonProperty("GUID")
	public String guid;
	@JsonProperty("ID")
	public String id;
	@JsonProperty("ERROR")
	public ERROR error;

	@Override
	public String toString() {
		return "GUID [submitDate=" + submitDate + ", guid=" + guid + ", id=" + id + ", error=" + error
				+ ", getSubmitDate()=" + getSubmitDate() + ", getGuid()=" + getGuid() + ", getId()=" + getId()
				+ ", getError()=" + getError() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass()
				+ ", toString()=" + super.toString() + "]";
	}

}
