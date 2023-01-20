package com.fpsteam.ratatouille2023.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fpsteam.ratatouille2023.entity.Avviso;
import com.fpsteam.ratatouille2023.repository.RepositoryAvviso;

@Service
public class ServiceAvviso{
	@Autowired
	private RepositoryAvviso repository;
		
	public Avviso save(Avviso a) {
		a.setData(new Date(System.currentTimeMillis()));
		return repository.save(a);
	}

}
