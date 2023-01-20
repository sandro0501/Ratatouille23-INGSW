package com.fpsteam.ratatouille2023.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Preparazione;
import com.fpsteam.ratatouille2023.repository.RepositoryPreparazione;

@Service
public class ServicePreparazione {
	@Autowired
	private RepositoryPreparazione repository;

	public void save(Preparazione preparazione) 
	{
		repository.save(preparazione);
	}

}
