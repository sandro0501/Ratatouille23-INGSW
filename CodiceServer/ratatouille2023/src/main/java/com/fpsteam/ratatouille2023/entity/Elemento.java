package com.fpsteam.ratatouille2023.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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

@Entity
@Table(name="elemento",
uniqueConstraints = { @UniqueConstraint (name = "ElementoUnicoPPerSezione", columnNames = {"denominazioneP","sezioneMenu"}),
				      @UniqueConstraint (name = "ElementoUnicoSPerSezione", columnNames = {"denominazioneS","sezioneMenu"})})
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Elemento {

	@Id
	@GeneratedValue
	private int idElemento;
	@NotNull
	private String denominazioneP;
	@Column(name="denominazioneS")
	@JsonProperty("denominazioneS")
	private String denominasioneS;
	@NotNull
	private String descrizioneP;
	@Column(name = "descrizioneS")
	private String descrizioneS;
	@NotNull
	@Min(0)
	private double costo;
	@NotNull
	@Min(0)
	private int posizione;
	//DEVI METTERE L'URL DELL'IMMAGINE
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="sezioneMenu")
	private SezioneMenu sezioneMenu;
	@OneToMany(mappedBy = "idElemento",cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JsonIgnore
	private Set<Preparazione> haBisognoDi = new HashSet<Preparazione>();
	@OneToMany(mappedBy = "idElemento", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JsonIgnore
	private Set<ListaAllergeni> haAllergeni = new HashSet<ListaAllergeni>();
	
	public Elemento() {}

	public int getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(int idElemento) {
		this.idElemento = idElemento;
	}

	public String getDenominazioneP() {
		return denominazioneP;
	}

	public void setDenominazioneP(String denominazioneP) {
		this.denominazioneP = denominazioneP;
	}

	public String getDenominasioneS() {
		return denominasioneS;
	}

	public void setDenominasioneS(String denominasioneS) {
		this.denominasioneS = denominasioneS;
	}

	public String getDescrizioneP() {
		return descrizioneP;
	}

	public void setDescrizioneP(String descrizioneP) {
		this.descrizioneP = descrizioneP;
	}

	public String getDescrizioneS() {
		return descrizioneS;
	}

	public void setDescrizioneS(String descrizioneS) {
		this.descrizioneS = descrizioneS;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(double costo) {
		this.costo = costo;
	}

	public int getPosizione() {
		return posizione;
	}

	public void setPosizione(int posizione) {
		this.posizione = posizione;
	}

	public SezioneMenu getSezioneMenu() {
		return sezioneMenu;
	}

	public void setSezioneMenu(SezioneMenu sezioneMenu) {
		this.sezioneMenu = sezioneMenu;
	}

	public Set<Preparazione> getHaBisognoDi() {
		return haBisognoDi;
	}

	public void setHaBisognoDi(Set<Preparazione> haBisognoDi) {
		this.haBisognoDi = haBisognoDi;
	}

	public Set<ListaAllergeni> getHaAllergeni() {
		return haAllergeni;
	}

	public void setHaAllergeni(Set<ListaAllergeni> haAllergeni) {
		this.haAllergeni = haAllergeni;
	}
	

}
