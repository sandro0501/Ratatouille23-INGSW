package com.fpsteam.ratatouille2023.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Utente;
import com.fpsteam.ratatouille2023.repository.RepositoryUtente;

@Service
public class ServiceUtente {
	@Autowired
	private RepositoryUtente repository;
	
	public Utente saveUtente(Utente product) {
		return repository.save(product);
	}

}
