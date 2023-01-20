package com.fpsteam.ratatouille2023.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fpsteam.ratatouille2023.primaryKeys.BachecaId;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bacheca")
@IdClass(BachecaId.class)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Bacheca {
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAvviso")
	@Id
	private Avviso idAvviso;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idUtente")
	@Id
	private Utente idUtente;
	@NotNull
	private boolean visibile = true;
	@NotNull
	private boolean visualizzato = false;
	
	public Bacheca() {}
	
	public Bacheca(@NotNull Avviso idAvviso, @NotNull Utente idUtente) {
		super();
		this.idAvviso = idAvviso;
		this.idUtente = idUtente;
	}
	public Avviso getIdAvviso() {
		return idAvviso;
	}
	public void setIdAvviso(Avviso idAvviso) {
		this.idAvviso = idAvviso;
	}
	public Utente getIdUtente() {
		return idUtente;
	}
	public void setIdUtente(Utente idUtente) {
		this.idUtente = idUtente;
	}
	public boolean isVisibile() {
		return visibile;
	}
	public void setVisibile(boolean visibile) {
		this.visibile = visibile;
	}
	public boolean isVisualizzato() {
		return visualizzato;
	}
	public void setVisualizzato(boolean visualizzato) {
		this.visualizzato = visualizzato;
	}
	
	

}
