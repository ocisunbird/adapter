package com.uci.adapter.netcore.whatsapp.outbound.nic;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
@XmlRootElement
public class MessageResponse {

	@JsonAlias({ "GUID" })
	public GUID guid;

	@Override
	public String toString() {
		return "MessageResponse [guid=" + guid + ", getGuid()=" + getGuid() + ", hashCode()=" + hashCode()
				+ ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
	}

}
