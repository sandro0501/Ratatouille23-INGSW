package com.fpsteam.ratatouille2023.primaryKeys;

import java.io.Serializable;
import java.util.Objects;

import com.fpsteam.ratatouille2023.entity.Avviso;
import com.fpsteam.ratatouille2023.entity.Utente;

public class BachecaId implements Serializable {
	
	private Avviso idAvviso;
	private Utente idUtente;
	
	public BachecaId(Avviso idAvviso, Utente idUtente) {
		super();
		this.idAvviso = idAvviso;
		this.idUtente = idUtente;
	}
	
	public BachecaId() {
	}
	
	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o==null || getClass() != o.getClass())return false;
		
		BachecaId bachecaId = (BachecaId) o;
		return idAvviso.equals(bachecaId.idAvviso) && idUtente.equals(bachecaId.idUtente);	
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(idAvviso,idUtente);
	}
	
}
