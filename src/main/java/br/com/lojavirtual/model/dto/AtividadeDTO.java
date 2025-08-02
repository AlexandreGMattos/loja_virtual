package br.com.lojavirtual.model.dto;

import java.io.Serializable;

public class AtividadeDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String text;
	private String code;
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

}
