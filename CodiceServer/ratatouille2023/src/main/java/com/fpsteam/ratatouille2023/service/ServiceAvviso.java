package com.fpsteam.ratatouille2023.service;

import java.sql.Date;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Avviso;
import com.fpsteam.ratatouille2023.entity.Bacheca;
import com.fpsteam.ratatouille2023.entity.Utente;
import com.fpsteam.ratatouille2023.repository.RepositoryAvviso;
import com.fpsteam.ratatouille2023.repository.RepositoryBacheca;
import com.fpsteam.ratatouille2023.repository.RepositoryUtente;

@Service
public class ServiceAvviso{
	@Autowired
	private RepositoryAvviso repository;
	@Autowired
	private RepositoryUtente repUt;
	@Autowired
	private RepositoryBacheca repBach;
		
	public Avviso save(Avviso a) {
		a.setData(new Date(System.currentTimeMillis()));
		return repository.save(a);
	}
	
	public Avviso saveAvvisoSistemaUrgente(Avviso a)
	{
		ArrayList<Utente> gestori = repUt.findByRank("Amministratore", a.getAutore().getidRistorante().getIdRistorante());
		gestori.addAll(repUt.findByRank("Supervisore", a.getAutore().getidRistorante().getIdRistorante()));
		ArrayList<Avviso> avvisiSistema = repository.findByUid(a.getAutore().getIdUtente());
		Avviso avvisoSistema;
		if(avvisiSistema.size()>0)
		{
			avvisoSistema = avvisiSistema.get(0);
			avvisoSistema.setCorpo(a.getCorpo());
			avvisoSistema.setOggetto(a.getOggetto());
			avvisoSistema.setData(a.getData());
			repository.save(avvisoSistema);
		}
		else
		{
			avvisoSistema = repository.save(a);
		}
		
		for(Utente x : gestori)
		{
			ArrayList<Bacheca> avvisiDaSistema = repBach.findByUid(a.getAutore().getIdUtente());
			boolean associato = false;
			for(Bacheca y : avvisiDaSistema)
			{
				if(y.getIdAvviso().getIdAvviso() == avvisoSistema.getIdAvviso())
				{
					associato = true;
					y.setVisibile(true);
					y.setVisualizzato(false);
					repBach.save(y);
				}
			}
			if(!associato)
			{
				Bacheca bacheca = new Bacheca(avvisoSistema,x);
				bacheca.setVisibile(true);
				bacheca.setVisualizzato(false);
				repBach.save(bacheca);
			}
			
		}
		return repository.save(a);
	}
	
	public Avviso saveAvvisoSistemaTransiente(Avviso a)
	{
		ArrayList<Utente> gestori = repUt.findByRank("Amministratore", a.getAutore().getidRistorante().getIdRistorante());
		gestori.addAll(repUt.findByRank("Supervisore", a.getAutore().getidRistorante().getIdRistorante()));
		ArrayList<Avviso> avvisiSistema = repository.findByUid(a.getAutore().getIdUtente());
		Avviso avvisoSistema;
		if(avvisiSistema.size()>0)
		{
			avvisoSistema = avvisiSistema.get(0);
			avvisoSistema.setCorpo(a.getCorpo());
			avvisoSistema.setOggetto(a.getOggetto());
			avvisoSistema.setData(a.getData());
			repository.save(avvisoSistema);
		}
		else
		{
			avvisoSistema = repository.save(a);
		}
		
		for(Utente x : gestori)
		{
			ArrayList<Bacheca> avvisiDaSistema = repBach.findByUid(a.getAutore().getIdUtente());
			boolean associato = false;
			for(Bacheca y : avvisiDaSistema)
			{
				if(y.getIdAvviso().getIdAvviso() == avvisoSistema.getIdAvviso())
				{
					associato = true;
					y.setVisibile(false);
					y.setVisualizzato(true);
					repBach.save(y);
				}
			}
			if(!associato)
			{
				Bacheca bacheca = new Bacheca(avvisoSistema,x);
				bacheca.setVisibile(true);
				bacheca.setVisualizzato(false);
				repBach.save(bacheca);
			}
			
		}
		return repository.save(a);
	}

}
