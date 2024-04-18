package com.uci.adapter.netcore.whatsapp.outbound.nic;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
public class Address {

	@SerializedName("@FROM")
	private String from;

	@SerializedName("@TO")
	private String to;

	@SerializedName("@SEQ")
	private String seq;

	@SerializedName("@TAG")
	private String tag;
}
