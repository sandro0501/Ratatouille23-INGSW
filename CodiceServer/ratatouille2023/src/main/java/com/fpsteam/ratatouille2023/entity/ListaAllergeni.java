package com.fpsteam.ratatouille2023.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fpsteam.ratatouille2023.primaryKeys.ListaAllergeniId;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table(name = "listaAllergeni")
@IdClass(ListaAllergeniId.class)
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ListaAllergeni {
	
	@Id
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idAllergene")
	private Allergene idAllergene;
	@Id
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idElemento")
	private Elemento idElemento;
	
	public ListaAllergeni() {}

	public Allergene getIdAllergene() {
		return idAllergene;
	}

	public void setIdAllergene(Allergene idAllergene) {
		this.idAllergene = idAllergene;
	}

	public Elemento getIdElemento() {
		return idElemento;
	}

	public void setIdElemento(Elemento idElemento) {
		this.idElemento = idElemento;
	}
	
	

}
