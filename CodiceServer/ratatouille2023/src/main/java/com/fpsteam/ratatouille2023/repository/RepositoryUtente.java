package com.fpsteam.ratatouille2023.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpsteam.ratatouille2023.entity.Utente;

public interface RepositoryUtente extends JpaRepository<Utente,Integer>{
	@Query(value = "SELECT * FROM utente WHERE email = ?1", nativeQuery = true)
	Utente findByEmail(String email);
	
	@Query(value = "SELECT * FROM utente WHERE id_ristorante = ?1", nativeQuery = true)
	List<Utente> findByRistorante(int idRistorante);
	
	@Query(value = "SELECT * FROM utente WHERE id_ristorante = ?2 AND ruolo = ?1", nativeQuery = true)
	ArrayList<Utente> findByRank(String string, int id_ristorante);


}
