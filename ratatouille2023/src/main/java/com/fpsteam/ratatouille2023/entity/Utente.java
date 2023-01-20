package com.fpsteam.ratatouille2023.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "utente")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Utente {
	
	@Id
	@GeneratedValue
	private int idUtente;
	@NotNull
	private String nome;
	@NotNull
	private String cognome;
	@Email
	@NotNull
	@Column(unique= true)
	private String email;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idRistorante")
	@NotNull
	@LazyToOne(LazyToOneOption.NO_PROXY)
	private Ristorante idRistorante;
	@NotNull
	private String ruolo;
	@NotNull
	private boolean master;
	@OneToMany(mappedBy = "autore",cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JsonIgnore
	private Set<Avviso> avvisi = new HashSet<Avviso>();
	@OneToMany(mappedBy = "idUtente", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JsonIgnore
	private Set<Bacheca> bacheca = new HashSet<Bacheca>();

	
	
	public Utente(String nome, String cognome, String email, String password, Ristorante ristorante, String ruolo) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.idRistorante = ristorante;
		this.ruolo = ruolo;
	}
	
	public Utente() {
		
	}
	
	public Utente(int idUtente) { 
		this.idUtente = idUtente;
	}
	
	public int getIdUtente() {
		return idUtente;
	}


	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}


	public boolean isMaster() {
		return master;
	}


	public void setMaster(boolean master) {
		this.master = master;
	}


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Ristorante getRistorante() {
		return idRistorante;
	}
	public void setRistorante(Ristorante ristorante) {
		this.idRistorante = ristorante;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	
	
}
