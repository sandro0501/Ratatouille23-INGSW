package com.fpsteam.ratatouille2023.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpsteam.ratatouille2023.entity.Preparazione;
import com.fpsteam.ratatouille2023.primaryKeys.PreparazioneId;

public interface RepositoryPreparazione extends JpaRepository<Preparazione,PreparazioneId>{

	@Query(
			value="SELECT * FROM preparazione WHERE id_elemento = ?1",
			nativeQuery = true
			)
	ArrayList<Preparazione> findByElId(int idElemento);

	@Query(
			value="SELECT * FROM preparazione WHERE id_prodotto = ?1",
			nativeQuery = true
			)
	ArrayList<Preparazione> findByPrId(int idProdotto);
	
}