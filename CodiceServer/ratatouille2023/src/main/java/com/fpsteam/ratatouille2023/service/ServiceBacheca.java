package com.fpsteam.ratatouille2023.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Avviso;
import com.fpsteam.ratatouille2023.entity.Bacheca;
import com.fpsteam.ratatouille2023.entity.Utente;
import com.fpsteam.ratatouille2023.handlers.EstraiBachecaResponseHandler;
import com.fpsteam.ratatouille2023.primaryKeys.BachecaId;
import com.fpsteam.ratatouille2023.repository.RepositoryBacheca;

@Service
public class ServiceBacheca {
	@Autowired
	private RepositoryBacheca repository;	
	
	public void utenteNascondeORiprendeAvviso(Utente ut, Avviso av)
	{
		BachecaId id = new BachecaId(av,ut);
		Bacheca res = repository.findById(id).get();
		res.setVisibile(res.isVisibile()? false : true);
		repository.save(res);	
	}
	
	
	public void utenteVisualizzaAvviso(Utente ut, Avviso av)
	{
		BachecaId id = new BachecaId(av,ut);
		Bacheca res = repository.findById(id).get();
		res.setVisualizzato(true);
		repository.save(res);	
	}
	
	public void salvaRelazioneUtenteAvviso(Utente ut, Avviso av)
	{
		
		Bacheca rel = new Bacheca();
		rel.setIdAvviso(av);
		rel.setIdUtente(ut);
		rel.setVisibile(true);
		//L'autore ha ovviamente visto il suo avviso
		if(ut.equals(av.getAutore()))
			rel.setVisualizzato(true);
		else
			rel.setVisualizzato(false);
		repository.save(rel);
	}

	public EstraiBachecaResponseHandler estraiAvvisi(int u) {
		EstraiBachecaResponseHandler handle = new EstraiBachecaResponseHandler();
		handle.nonVisualizzati = (List<Avviso>) (Object) repository.findByVisualizzato(u,false);
		handle.nonVisibili = (List<Avviso>) (Object) repository.findNonVisibili(u);
		handle.visibili = (List<Avviso>) (Object) repository.findVisibili(u);
		return handle;
	}

		
}
