package com.uci.adapter.netcore.whatsapp.outbound.nic;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Builder;
import lombok.Data;

@Data
@XmlRootElement
public class GUID {

	@JsonAlias({ "SUBMITDATE" })
	public String submitDate;
	@JsonAlias({ "GUID" })
	public String guid;
	@JsonAlias({ "ID" })
	public String id;
	@JsonAlias({ "ERROR" })
	public ERROR error;

	@Override
	public String toString() {
		return "GUID [submitDate=" + submitDate + ", guid=" + guid + ", id=" + id + ", error=" + error
				+ ", getSubmitDate()=" + getSubmitDate() + ", getGuid()=" + getGuid() + ", getId()=" + getId()
				+ ", getError()=" + getError() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass()
				+ ", toString()=" + super.toString() + "]";
	}

}
