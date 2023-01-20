package com.fpsteam.ratatouille2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpsteam.ratatouille2023.entity.Preparazione;
import com.fpsteam.ratatouille2023.primaryKeys.PreparazioneId;

public interface RepositoryPreparazione extends JpaRepository<Preparazione,PreparazioneId>{
	
}