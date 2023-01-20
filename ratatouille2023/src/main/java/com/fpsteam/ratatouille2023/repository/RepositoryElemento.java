package com.fpsteam.ratatouille2023.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpsteam.ratatouille2023.entity.Elemento;

public interface RepositoryElemento extends JpaRepository<Elemento,Integer>{

	@Query(
			value = "SELECT * FROM elemento WHERE sezione_menu = ?1 ORDER BY posizione ASC",
			nativeQuery = true
			)
	ArrayList<Elemento> findBySezione(int idAvviso);
	
}