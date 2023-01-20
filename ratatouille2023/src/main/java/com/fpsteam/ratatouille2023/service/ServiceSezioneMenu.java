package com.fpsteam.ratatouille2023.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Ristorante;
import com.fpsteam.ratatouille2023.entity.SezioneMenu;
import com.fpsteam.ratatouille2023.repository.RepositorySezioneMenu;

@Service
public class ServiceSezioneMenu {
	@Autowired
	private RepositorySezioneMenu repository;

	public ArrayList<SezioneMenu> findByIdRistorante(Ristorante ristorante) {
		
		return repository.findByIdRistorante(ristorante.getIdRistorante());
	}

	public void save(SezioneMenu sezione) 
	{
		repository.save(sezione);
	}

}
