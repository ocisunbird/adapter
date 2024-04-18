package com.uci.adapter.netcore.whatsapp.outbound.nic;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@XmlRootElement
public class NewSendMessageResponse {

	@JsonProperty("MESSAGEACK")
	public MESSAGEACK mESSAGEACK;

	@Override
	public String toString() {
		return "NewSendMessageResponse [mESSAGEACK=" + mESSAGEACK + ", getMESSAGEACK()=" + getMESSAGEACK()
				+ ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
				+ "]";
	}

}
