package com.fpsteam.ratatouille2023.service;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.Preparazione;
import com.fpsteam.ratatouille2023.primaryKeys.PreparazioneId;
import com.fpsteam.ratatouille2023.repository.RepositoryPreparazione;

@Service
public class ServicePreparazione {
	@Autowired
	private RepositoryPreparazione repository;

	public void save(Preparazione preparazione) 
	{
		repository.save(preparazione);
	}

	public void update(Preparazione preparazione) {
		PreparazioneId id = new PreparazioneId();
		id.setIdElemento(preparazione.getIdElemento());
		id.setIdProdotto(preparazione.getIdProdotto());
		Preparazione estratto = repository.findById(id).get();
		estratto =preparazione;
		repository.save(preparazione);
		
	}
	
	public String delete(ArrayList<Preparazione> preparazione) {
		PreparazioneId id = new PreparazioneId();
		for(int x = 0; x<preparazione.size(); x++)
		{
			id.setIdElemento(preparazione.get(x).getIdElemento());
			id.setIdProdotto(preparazione.get(x).getIdProdotto());
			Preparazione res = repository.findById(id).get();
			repository.delete(res);
		}
		return "Tutto bene";
	}

}
