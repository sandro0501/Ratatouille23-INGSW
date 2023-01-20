package com.fpsteam.ratatouille2023.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.ListaAllergeni;
import com.fpsteam.ratatouille2023.repository.RepositoryListaAllergeni;

@Service
public class ServiceListaAllergeni {
	@Autowired
	private RepositoryListaAllergeni repository;
	
	public void save(ListaAllergeni lista)
	{
		repository.save(lista);
	}


}
