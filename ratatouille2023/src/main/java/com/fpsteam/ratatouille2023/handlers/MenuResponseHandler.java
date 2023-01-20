package com.fpsteam.ratatouille2023.handlers;

import java.util.ArrayList;
import java.util.List;

import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.SezioneMenu;

public class MenuResponseHandler {
	public SezioneMenu sezione;
	//Crea nuova classe, elementoHandler, che contiene l'elemento,
	//poi in separata sede una lista di allergeni e una di Prodotto (preparazione)
	public ArrayList<ElementoHandler> elementi = new ArrayList<ElementoHandler>();
	
	public MenuResponseHandler() {}
	
	
	public MenuResponseHandler(SezioneMenu sezione) {
		super();
		this.sezione = sezione;
	}
	
}
