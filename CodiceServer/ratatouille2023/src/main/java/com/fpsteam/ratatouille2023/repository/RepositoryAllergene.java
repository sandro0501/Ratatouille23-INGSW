package com.fpsteam.ratatouille2023.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpsteam.ratatouille2023.entity.Allergene;

public interface RepositoryAllergene extends JpaRepository<Allergene,Integer>{

	@Query(
			value="SELECT a.id_allergene, a.nome FROM allergene as a, lista_allergeni as l where l.id_allergene = a.id_allergene AND l.id_elemento = ?1",
			nativeQuery = true
			)
	ArrayList<Allergene> findByElemento(int idElemento);
	
}