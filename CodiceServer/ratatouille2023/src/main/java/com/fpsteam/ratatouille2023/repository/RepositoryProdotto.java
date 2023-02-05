package com.fpsteam.ratatouille2023.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpsteam.ratatouille2023.entity.Prodotto;
import com.fpsteam.ratatouille2023.handlers.RichiestaPreparazioneHandler;

import jakarta.persistence.Tuple;

public interface RepositoryProdotto extends JpaRepository<Prodotto,Integer>{
	
	@Query(
			value="SELECT p.id_prodotto, p.nome, p.descrizione, p.unita, pr.quantita FROM prodotto as p, preparazione as pr WHERE pr.id_prodotto = p.id_prodotto AND pr.id_elemento = ?1",
			nativeQuery = true
			)
	ArrayList<Tuple> findByElemento(int idElemento);
	
	@Query(
			value= "SELECT * FROM prodotto WHERE id_ristorante = ?1 ",
			nativeQuery = true
			)
	ArrayList<Prodotto> findAllByRistorante(int idRistorante);

	@Query(
			value="SELECT * FROM prodotto WHERE id_ristorante = ?1 AND soglia>quantita",
			nativeQuery = true
			)
	ArrayList<Prodotto> findInScadenza(int idRistorante);

}