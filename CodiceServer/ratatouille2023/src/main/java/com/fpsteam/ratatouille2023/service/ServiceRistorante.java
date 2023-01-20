package com.fpsteam.ratatouille2023.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Ristorante;
import com.fpsteam.ratatouille2023.repository.RepositoryRistorante;

@Service
public class ServiceRistorante {
	@Autowired
	private RepositoryRistorante repository;
	
	public Ristorante findById(Ristorante ristorante)
	{
		Optional<Ristorante> res = repository.findById(ristorante.getIdRistorante());
		return res.get();
	}
	
	public Ristorante saveRistorante(Ristorante ristorante) {
		return repository.save(ristorante);
	}

}