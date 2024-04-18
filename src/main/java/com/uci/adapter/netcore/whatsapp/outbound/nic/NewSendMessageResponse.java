package com.uci.adapter.netcore.whatsapp.outbound.nic;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
@XmlRootElement
public class NewSendMessageResponse {

	@JsonAlias({ "MESSAGEACK" })
	public MessageResponse messageResponse;

	@Override
	public String toString() {
		return "NewSendMessageResponse [messageResponse=" + messageResponse + ", getMessageResponse()="
				+ getMessageResponse() + ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()="
				+ super.toString() + "]";
	}

}
