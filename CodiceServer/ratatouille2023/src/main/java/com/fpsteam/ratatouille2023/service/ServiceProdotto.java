package com.fpsteam.ratatouille2023.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.Preparazione;
import com.fpsteam.ratatouille2023.entity.Prodotto;
import com.fpsteam.ratatouille2023.handlers.RichiestaPreparazioneHandler;
import com.fpsteam.ratatouille2023.repository.RepositoryProdotto;

import jakarta.persistence.Tuple;

@Service
public class ServiceProdotto {
	@Autowired
	private RepositoryProdotto repository;
		
	public ArrayList<RichiestaPreparazioneHandler> findByElemento(Elemento elemento)
	{

		ArrayList<Tuple> res = repository.findByElemento(elemento.getIdElemento());
		ArrayList<RichiestaPreparazioneHandler> ret = new ArrayList<RichiestaPreparazioneHandler>();
		
		for(Tuple x : res)
		{
			ret.add(new RichiestaPreparazioneHandler((int)x.get(0),(String)x.get(1),(String)x.get(2),(double)x.get(3)));
		}
		
		
		return ret;
	}

	public void save(Prodotto prodotto) 
	{
		repository.save(prodotto);
	}

}
