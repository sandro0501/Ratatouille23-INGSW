package com.fpsteam.ratatouille2023.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.entity.Ristorante;
import com.fpsteam.ratatouille2023.entity.SezioneMenu;
import com.fpsteam.ratatouille2023.repository.RepositoryElemento;
import com.fpsteam.ratatouille2023.repository.RepositoryRistorante;

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
	


}
