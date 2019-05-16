package com.spring.internship.service.Impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.internship.model.Skill;
import com.spring.internship.model.Technology;
import com.spring.internship.repository.SkillRepository;
import com.spring.internship.service.ISkillService;

@Service
@Transactional
public class SkillServiceImpl implements ISkillService {

	@Autowired
	SkillRepository repo;
	
	@Override
	public Skill save(Skill entity) {
		return repo.save(entity);
	}

	@Override
	public Skill update(Skill entity) {
		return repo.save(entity);
	}

	@Override
	public List<Skill> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Skill> getById(Long id) {
		return repo.findById(id);
	}

	@Override
	public void remove(Long id) {
		repo.deleteById(id);
	}

}
