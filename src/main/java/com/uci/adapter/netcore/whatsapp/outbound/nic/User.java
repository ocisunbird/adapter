package com.uci.adapter.netcore.whatsapp.outbound.nic;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
public class User {

	@SerializedName("@CH_TYPE")
	private String channelType;

	@SerializedName("@UNIXTIMESTAMP")
	private String timestamp;
}
