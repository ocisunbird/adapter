package com.uci.adapter.netcore.whatsapp.outbound.nic;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@XmlRootElement
public class MESSAGEACK {

	@JsonProperty("GUID")
	public GUID guid;

	@Override
	public String toString() {
		return "MESSAGEACK [guid=" + guid + ", getGuid()=" + getGuid() + ", hashCode()=" + hashCode() + ", getClass()="
				+ getClass() + ", toString()=" + super.toString() + "]";
	}

}
