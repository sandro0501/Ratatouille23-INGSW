package com.fpsteam.ratatouille2023.entity;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "avviso" )
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Avviso {
	
	@Id
	@GeneratedValue
	private int idAvviso;
	@NotNull
	private String oggetto;
	@NotNull
	private String corpo;
	@NotNull
	private Date data;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "autore")
	private Utente autore;
	
	public Avviso() {}
	
	
	public int getIdAvviso() {
		return idAvviso;
	}
	public void setIdAvviso(int idAvviso) {
		this.idAvviso = idAvviso;
	}
	public Utente getAutore() {
		return autore;
	}
	public void setAutore(Utente autore) {
		this.autore = autore;
	}
	public Avviso(String oggetto, String corpo, Date data) {
		super();
		this.oggetto = oggetto;
		this.corpo = corpo;
		this.data = data;
	}
	public String getOggetto() {
		return oggetto;
	}
	public void setOggetto(String oggetto) {
		this.oggetto = oggetto;
	}
	public String getCorpo() {
		return corpo;
	}
	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	
	

}
