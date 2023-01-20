package com.fpsteam.ratatouille2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fpsteam.ratatouille2023.primaryKeys.PreparazioneId;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "preparazione")
@IdClass(PreparazioneId.class)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Preparazione {
	@Id
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idElemento")
	private Elemento idElemento;
	@Id
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProdotto")
	private Prodotto idProdotto;	
	@Id
	@NotNull
	@Min(0)
	private double quantita;
	
	
	public Preparazione() {}


	public Elemento getIdElemento() {
		return idElemento;
	}


	public void setIdElemento(Elemento idElemento) {
		this.idElemento = idElemento;
	}


	public Prodotto getIdProdotto() {
		return idProdotto;
	}


	public void setIdProdotto(Prodotto idProdotto) {
		this.idProdotto = idProdotto;
	}


	public double getQuantita() {
		return quantita;
	}


	public void setQuantita(double quantita) {
		this.quantita = quantita;
	}
	
	
}
