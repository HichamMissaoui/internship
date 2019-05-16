package com.spring.internship.service.Impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.internship.model.Skill;
import com.spring.internship.model.Technology;
import com.spring.internship.repository.TechnologyRepository;
import com.spring.internship.service.ITechnologyService;

@Service
@Transactional
public class TechnologyServiceImpl implements ITechnologyService {
	
	@Autowired
	TechnologyRepository repo;

	@Override
	public Technology save(Technology entity) {
		return repo.save(entity);
	}

	@Override
	public Technology update(Technology entity) {
		return repo.save(entity);
	}

	@Override
	public List<Technology> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Technology> getById(Long id) {
		return repo.findById(id);
	}

	@Override
	public void remove(Long id) {
		repo.deleteById(id);		
	}

}
