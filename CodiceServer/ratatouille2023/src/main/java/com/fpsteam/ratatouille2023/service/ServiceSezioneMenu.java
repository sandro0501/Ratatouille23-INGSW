package com.fpsteam.ratatouille2023.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.Ristorante;
import com.fpsteam.ratatouille2023.entity.SezioneMenu;
import com.fpsteam.ratatouille2023.repository.RepositoryElemento;
import com.fpsteam.ratatouille2023.repository.RepositorySezioneMenu;

@Service
public class ServiceSezioneMenu {
	@Autowired
	private RepositorySezioneMenu repository;
	@Autowired
	private ServiceElemento serviceEl;
	@Autowired
	private RepositoryElemento repEl;

	public ArrayList<SezioneMenu> findByIdRistorante(int ristorante) {
		
		return repository.findByIdRistorante(ristorante);
	}

	public void save(SezioneMenu sezione) 
	{
		repository.save(sezione);
	}

	public String delete(ArrayList<SezioneMenu> sezioni) {
		for(int x = 0; x<sezioni.size(); x++)
		{
			serviceEl.delete(repEl.findBySezione(sezioni.get(x).getIdAvviso()));
			
			SezioneMenu res = repository.findById(sezioni.get(x).getIdAvviso()).get();
			repository.delete(res);
		}
		return "Tutto bene";
	}

	public void update(SezioneMenu sezione) {
		SezioneMenu sezCurr = repository.findById(sezione.getIdAvviso()).get();
		sezCurr = sezione;
		save(sezCurr);
	}
	
}
