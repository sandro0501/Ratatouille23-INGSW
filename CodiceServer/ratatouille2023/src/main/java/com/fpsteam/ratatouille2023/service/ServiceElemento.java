package com.fpsteam.ratatouille2023.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.SezioneMenu;
import com.fpsteam.ratatouille2023.repository.RepositoryElemento;

@Service
public class ServiceElemento {
	@Autowired
	private RepositoryElemento repository;

	public ArrayList<Elemento> findBySezione(SezioneMenu sezione) {
		
		return repository.findBySezione(sezione.getIdAvviso());
	}

	public void save(Elemento elemento) {
		repository.save(elemento);
	}

	public void update(Elemento elemento) {
		Elemento estratto = repository.findById(elemento.getIdElemento()).get();
		estratto = elemento;
		repository.save(elemento);
		
	}

	public String delete(ArrayList<Elemento> elemento) {
		for(int x = 0; x<elemento.size(); x++)
		{
			Elemento res = repository.findById(elemento.get(x).getIdElemento()).get();
			repository.delete(res);
		}
		return "Tutto bene";
	}
	


}
