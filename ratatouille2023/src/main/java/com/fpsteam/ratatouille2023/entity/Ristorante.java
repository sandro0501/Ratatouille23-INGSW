package com.fpsteam.ratatouille2023.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "ristorante")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Ristorante {
	
	
	@Id
	@GeneratedValue
	private int idRistorante;
	@NotNull
	private String denominazione = "IlTuoRistorante";
	@NotNull
	private String numeroTelefono = "IlTuoRecapito";
	@NotNull
	private String indirizzo = "IlTuoIndirizzo";
	@NotNull
	private String citta = "LaTuaCitta";
	@NotNull
	private boolean turistico = false;
	@OneToMany(mappedBy = "idRistorante", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JsonIgnore
	private Set<Utente> impiegati = new HashSet<Utente>();
	@OneToMany(mappedBy = "ristorante", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JsonIgnore
	private Set<SezioneMenu> sezioni = new HashSet<SezioneMenu>();
	@OneToMany(mappedBy = "ristorante", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JsonIgnore
	private Set<Prodotto> prodotti = new HashSet<Prodotto>();
	private String urlFoto;
	
	public Ristorante() {
		
	}
	
	public Ristorante(String denominazione, String numeroTelefono, String indirizzo, String citta, boolean turistico,
			String urlFoto) {
		super();
		this.denominazione = denominazione;
		this.numeroTelefono = numeroTelefono;
		this.indirizzo = indirizzo;
		this.citta = citta;
		this.turistico = turistico;
		this.urlFoto = urlFoto;
	}
	public Ristorante(String denominazione, String numeroTelefono, String indirizzo, String citta, boolean turistico) {
		super();
		this.denominazione = denominazione;
		this.numeroTelefono = numeroTelefono;
		this.indirizzo = indirizzo;
		this.citta = citta;
		this.turistico = turistico;
	}
	
	 
	
	public void setIdRistorante(int idRistorante) {
		this.idRistorante = idRistorante;
	}

	public void setImpiegati(Set<Utente> impiegati) {
		this.impiegati = impiegati;
	}

	public int getIdRistorante() {
		return idRistorante;
	}

	public Set<Utente> getImpiegati() {
		return impiegati;
	}

	public String getDenominazione() {
		return denominazione;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public String getNumeroTelefono() {
		return numeroTelefono;
	}
	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}
	public String getIndirizzo() {
		return indirizzo;
	}
	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}
	public String getCitta() {
		return citta;
	}
	public void setCitta(String citta) {
		this.citta = citta;
	}
	public boolean isTuristico() {
		return turistico;
	}
	public void setTuristico(boolean turistico) {
		this.turistico = turistico;
	}
	public String getUrlFoto() {
		return urlFoto;
	}
	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}
	
	

}
