package com.fpsteam.ratatouille2023.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.Preparazione;
import com.fpsteam.ratatouille2023.entity.Prodotto;
import com.fpsteam.ratatouille2023.entity.Ristorante;
import com.fpsteam.ratatouille2023.handlers.RichiestaPreparazioneHandler;
import com.fpsteam.ratatouille2023.repository.RepositoryPreparazione;
import com.fpsteam.ratatouille2023.repository.RepositoryProdotto;

import jakarta.persistence.Tuple;

@Service
public class ServiceProdotto {
	@Autowired
	private RepositoryProdotto repository;
	@Autowired
	private RepositoryPreparazione repP;
		
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

	public ArrayList<Prodotto> findAll(int ristorant) {
		return repository.findAllByRistorante(ristorant);
		
	}

	public String update(Prodotto prodotto) {
		Prodotto updating = repository.findById(prodotto.getIdProdotto()).get();
		updating.setCosto(prodotto.getCosto());
		updating.setDescrizione(prodotto.getDescrizione());
		updating.setNome(prodotto.getNome());
		updating.setQuantita(prodotto.getQuantita());
		updating.setSoglia(prodotto.getSoglia());
		updating.setUnita(prodotto.getUnita());
		repository.save(updating);
		return "Tutto bene";
	}

	public String delete(ArrayList<Prodotto> prodotto) {
		for(int x = 0; x<prodotto.size(); x++)
		{
			repP.deleteAll(repP.findByPrId(prodotto.get(x).getIdProdotto()));
			
			Prodotto res = repository.findById(prodotto.get(x).getIdProdotto()).get();
			repository.delete(res);
		}
		return "Tutto bene";
	}

}
