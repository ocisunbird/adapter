package com.uci.adapter.netcore.whatsapp.outbound.nic;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
@XmlRootElement
public class ERROR {

	@JsonAlias({ "CODE" })
	public int code;
	@JsonAlias({ "SEQ" })
	public int seq;

	@Override
	public String toString() {
		return "ERROR [code=" + code + ", seq=" + seq + ", getCode()=" + getCode() + ", getSeq()=" + getSeq()
				+ ", hashCode()=" + hashCode() + ", getClass()=" + getClass() + ", toString()=" + super.toString()
				+ "]";
	}

}
