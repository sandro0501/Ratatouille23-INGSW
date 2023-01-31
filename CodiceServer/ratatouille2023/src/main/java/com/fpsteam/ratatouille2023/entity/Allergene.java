package com.fpsteam.ratatouille2023.entity;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "allergene")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Allergene {
	@Id
	@GeneratedValue
	private int idAllergene;
 
	@NotNull
	@Column(unique = true)
	private Valid nome;
	
	@OneToMany(mappedBy = "idAllergene", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	@JsonIgnore
	private Set<ListaAllergeni> presenteIn = new HashSet<ListaAllergeni>();
	
	public Allergene() {
		
	}
	
	
	public int getIdAllergene() {
		return idAllergene;
	}



	public void setIdAllergene(int idAllergene) {
		this.idAllergene = idAllergene;
	}



	public Set<ListaAllergeni> getPresenteIn() {
		return presenteIn;
	}



	public void setPresenteIn(Set<ListaAllergeni> presenteIn) {
		this.presenteIn = presenteIn;
	}



	public Valid getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = Valid.valueOf(nome);
	}

	public Allergene(Valid v) {
		this.nome = v;
	}


	public enum Valid{
		Crostacei,Molluschi,Uova,Soia,Sedano,Solfiti,Lupini,Lattosio,Pesce,Senape,Noci,Arachidi,Sesamo,Glutine 
	}
}
