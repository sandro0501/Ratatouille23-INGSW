package com.fpsteam.ratatouille2023.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpsteam.ratatouille2023.entity.ListaAllergeni;
import com.fpsteam.ratatouille2023.primaryKeys.ListaAllergeniId;

public interface RepositoryListaAllergeni extends JpaRepository<ListaAllergeni,ListaAllergeniId>{

	@Query(
			value= "SELECT * FROM lista_allergeni WHERE id_elemento = ?1",
			nativeQuery = true
			)
	ArrayList<ListaAllergeni> findByElId(int idElemento);
	
}