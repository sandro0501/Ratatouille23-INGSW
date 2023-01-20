package com.fpsteam.ratatouille2023.handlers;

import java.util.ArrayList;

import com.fpsteam.ratatouille2023.entity.Allergene;
import com.fpsteam.ratatouille2023.entity.Elemento;

public class ElementoHandler {
	
	public Elemento elemento;
	public ArrayList<Allergene> allergeni = new ArrayList<Allergene>();
	public ArrayList<ProdottoHandler> preparazione = new ArrayList<ProdottoHandler>();
	
	public ElementoHandler(Elemento e) {
		elemento = e;
	}

}
