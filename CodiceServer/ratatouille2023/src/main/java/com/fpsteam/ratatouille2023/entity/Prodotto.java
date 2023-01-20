package com.fpsteam.ratatouille2023.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "prodotto",
		uniqueConstraints = @UniqueConstraint (name = "ElementoUnicoPPerSezione", columnNames = {"nome","idRistorante"}))
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@AllArgsConstructor
public class Prodotto {
	@Id
	@GeneratedValue
	private int idProdotto;
	@NotNull
	@Column(name="nome")
	private String nome;
	@NotNull
	private String descrizione;
	@NotNull
	private String unita;
	@NotNull
	private String costo;
	@NotNull
	@Min(0)
	private double quantita;
	@NotNull
	@Min(0)
	private double soglia;
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idRistorante")
	private Ristorante ristorante;
	@OneToMany(mappedBy = "idProdotto",cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JsonIgnore
	private Set<Preparazione> usatoIn = new HashSet<Preparazione>();
	
	public Prodotto() 
	{
		
	}

	public Prodotto(int idProdotto, @NotNull String nome, @NotNull String unita) {
		super();
		this.idProdotto = idProdotto;
		this.nome = nome;
		this.unita = unita;
	}

	public int getIdProdotto() {
		return idProdotto;
	}

	public void setIdProdotto(int idProdotto) {
		this.idProdotto = idProdotto;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getUnita() {
		return unita;
	}

	public void setUnita(String unita) {
		this.unita = unita;
	}

	public String getCosto() {
		return costo;
	}

	public void setCosto(String costo) {
		this.costo = costo;
	}

	public double getQuantita() {
		return quantita;
	}

	public void setQuantita(double quantita) {
		this.quantita = quantita;
	}

	public double getSoglia() {
		return soglia;
	}

	public void setSoglia(double soglia) {
		this.soglia = soglia;
	}

	public Ristorante getRistorante() {
		return ristorante;
	}

	public void setRistorante(Ristorante ristorante) {
		this.ristorante = ristorante;
	}

	public Set<Preparazione> getUsatoIn() {
		return usatoIn;
	}

	public void setUsatoIn(Set<Preparazione> usatoIn) {
		this.usatoIn = usatoIn;
	}
	
	
	
	
}
