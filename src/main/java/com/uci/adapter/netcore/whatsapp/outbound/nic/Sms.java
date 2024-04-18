package com.uci.adapter.netcore.whatsapp.outbound.nic;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@XmlRootElement
public class Sms {

	@SerializedName("@UDH")
	private String udh;

	@SerializedName("@CODING")
	private String coding;

	@SerializedName("@TEXT")
	private String text;

	@SerializedName("@TEMPLATEINFO")
	private String templateInfo;

	@SerializedName("@PROPERTY")
	private String property;

	@SerializedName("@MSGTYPE")
	private String messageType;

	@SerializedName("@ID")
	private String id;

	@SerializedName("ADDRESS")
	private List<Address> address;

}
