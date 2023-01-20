package com.fpsteam.ratatouille2023.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpsteam.ratatouille2023.entity.ListaAllergeni;
import com.fpsteam.ratatouille2023.primaryKeys.ListaAllergeniId;

public interface RepositoryListaAllergeni extends JpaRepository<ListaAllergeni,ListaAllergeniId>{
	
}