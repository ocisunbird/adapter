package com.uci.adapter.netcore.whatsapp.outbound.nic;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
@XmlRootElement
public class MessageResponse {

	@JsonAlias({ "GUID" })
	public GUID guid;

}
