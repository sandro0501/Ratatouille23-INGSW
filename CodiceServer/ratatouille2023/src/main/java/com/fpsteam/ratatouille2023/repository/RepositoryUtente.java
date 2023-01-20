package com.fpsteam.ratatouille2023.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpsteam.ratatouille2023.entity.Utente;

public interface RepositoryUtente extends JpaRepository<Utente,Integer>{
	@Query(value = "SELECT * FROM UTENTE WHERE email = ?1", nativeQuery = true)
	Utente findByEmail(String email);
	
	@Query(value = "SELECT * FROM UTENTE WHERE id_ristorante = ?1", nativeQuery = true)
	List<Utente> findByRistorante(int idRistorante);

}
