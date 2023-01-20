package com.fpsteam.ratatouille2023.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpsteam.ratatouille2023.entity.Avviso;
import com.fpsteam.ratatouille2023.entity.Bacheca;
import com.fpsteam.ratatouille2023.primaryKeys.BachecaId;

public interface RepositoryBacheca extends JpaRepository<Bacheca,BachecaId> {
	@Query(
			value="SELECT a.corpo, a.oggetto, a.data, a.autore FROM bacheca as b, avviso as a WHERE b.id_utente = ?1 AND b.id_avviso = a.id_avviso AND b.visualizzato = ?2",
			nativeQuery = true
			)
	List<Object> findByVisualizzato(int uid, boolean visualizzato);
	
	@Query(
			value="SELECT a.corpo, a.oggetto, a.data, a.autore FROM bacheca as b, avviso as a WHERE b.id_utente = ?1 AND b.id_avviso = a.id_avviso AND b.visibile = 1 AND b.visualizzato = 1",
			nativeQuery = true
			)
	List<Object> findVisibili(int uid);
	
	@Query(
			value = "SELECT a.corpo, a.oggetto, a.data, a.autore FROM bacheca as b, avviso as a WHERE b.id_utente = ?1 AND b.id_avviso = a.id_avviso AND b.visibile = 0",
			nativeQuery = true
			)
	List<Object> findNonVisibili(int uid);
	
	@Query(
			value = "SELECT * FROM bacheca WHERE id_avviso = ?1 AND id_utente = ?2",
			nativeQuery = true
			)
	Bacheca findById(int id_avv, int id_ute);
}
