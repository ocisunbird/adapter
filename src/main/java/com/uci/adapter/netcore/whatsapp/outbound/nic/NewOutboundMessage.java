package com.uci.adapter.netcore.whatsapp.outbound.nic;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
public class NewOutboundMessage {

	@SerializedName("@VER")
	private String version;

	@SerializedName("USER")
	private User user;

	@SerializedName("SMS")
	private List<Sms> sms;
}
