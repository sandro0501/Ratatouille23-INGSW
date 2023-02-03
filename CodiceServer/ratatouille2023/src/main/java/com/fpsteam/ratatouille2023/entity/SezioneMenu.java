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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "sezioneMenu")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class SezioneMenu {
	
	@Id
	@GeneratedValue
	private int idAvviso;
	@NotNull
	private String titolo;
	@NotNull
	@Min(0)
	private int posizione;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ristorante")
	private Ristorante ristorante;
	
	@OneToMany(mappedBy = "sezioneMenu", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JsonIgnore
	private Set<Elemento> elementi = new HashSet<Elemento>();
	
	public SezioneMenu() {}

	public int getIdAvviso() {
		return idAvviso;
	}

	public void setIdAvviso(int idAvviso) {
		this.idAvviso = idAvviso;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public int getPosizione() {
		return posizione;
	}

	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}

	public Ristorante getRistorante() {
		return ristorante;
	}

	public void setRistorante(Ristorante ristorante) {
		this.ristorante = ristorante;
	}

	public Set<Elemento> getElementi() {
		return elementi;
	}

	public void setElementi(Set<Elemento> elementi) {
		this.elementi = elementi;
	}
	
	
	
}
