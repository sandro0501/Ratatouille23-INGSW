package com.fpsteam.ratatouille2023.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "utente")
public class Utente {
	
	@Id
	@GeneratedValue
	private int idUtente;
	private String nome;
	private String cognome;
	private String email;
	private String password;
	private String ristorante;
	private String ruolo;
	
	
	
	public Utente(String nome, String cognome, String email, String password, String ristorante, String ruolo) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.email = email;
		this.password = password;
		this.ristorante = ristorante;
		this.ruolo = ruolo;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRistorante() {
		return ristorante;
	}
	public void setRistorante(String ristorante) {
		this.ristorante = ristorante;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	
	
}
