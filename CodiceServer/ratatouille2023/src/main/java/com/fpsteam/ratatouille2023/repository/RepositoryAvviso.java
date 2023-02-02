package com.fpsteam.ratatouille2023.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpsteam.ratatouille2023.entity.Avviso;

public interface RepositoryAvviso extends JpaRepository<Avviso,Integer> {

	@Query(
			value= "SELECT * FROM avviso WHERE autore = ?1",
			nativeQuery = true
			)
	ArrayList<Avviso> findByUid(int idUtente);

}
