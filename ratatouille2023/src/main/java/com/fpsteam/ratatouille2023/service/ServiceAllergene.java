package com.fpsteam.ratatouille2023.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Allergene;
import com.fpsteam.ratatouille2023.entity.Elemento;
import com.fpsteam.ratatouille2023.repository.RepositoryAllergene;

@Service
public class ServiceAllergene {
	@Autowired
	private RepositoryAllergene repository;
	
	public void createIfNotThere() {
		List<Allergene> res = repository.findAll();
		if (res.size() != 14) {
			for(Allergene.Valid x : Allergene.Valid.values()) {
				repository.save(new Allergene(x));
			}
		}
		
	}
	
	public ArrayList<Allergene> findByElemento(Elemento e)
	{
		return repository.findByElemento(e.getIdElemento());
	}

}
