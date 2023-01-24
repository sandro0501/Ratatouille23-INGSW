package com.fpsteam.ratatouille2023.primaryKeys;

import java.io.Serializable;
import java.util.Objects;

import com.fpsteam.ratatouille2023.entity.Allergene;
import com.fpsteam.ratatouille2023.entity.Elemento;


public class ListaAllergeniId implements Serializable{

	private Allergene idAllergene;
	private Elemento idElemento;
	
	public ListaAllergeniId() {
		
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o==null || getClass() != o.getClass())return false;
		
		ListaAllergeniId listaAllergeniId = (ListaAllergeniId) o;
		return idAllergene.equals(listaAllergeniId.idAllergene) && idElemento.equals(listaAllergeniId.idElemento);	
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idAllergene,idElemento);
	}

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
