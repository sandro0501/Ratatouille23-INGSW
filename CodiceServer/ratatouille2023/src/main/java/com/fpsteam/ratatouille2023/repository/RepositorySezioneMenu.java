package com.fpsteam.ratatouille2023.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpsteam.ratatouille2023.entity.SezioneMenu;

public interface RepositorySezioneMenu extends JpaRepository<SezioneMenu,Integer>{
	@Query(
			value="SELECT * FROM sezione_menu WHERE ristorante= ?1 ORDER BY posizione ASC",
			nativeQuery = true
			)
	ArrayList<SezioneMenu> findByIdRistorante(int idRistorante);
	
}