package com.fpsteam.ratatouille2023.primaryKeys;

import java.io.Serializable;
import java.util.Objects;

import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.Prodotto;


public class PreparazioneId implements Serializable{

	private Elemento idElemento;
	private Prodotto idProdotto;
	private double quantita;
	
	public PreparazioneId() {
		
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o==null || getClass() != o.getClass())return false;
		
		PreparazioneId preparazioneId = (PreparazioneId) o;
		return idProdotto.equals(preparazioneId.idProdotto) && idElemento.equals(preparazioneId.idElemento) && quantita == preparazioneId.quantita;	
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idProdotto,idElemento);
	}
	
}