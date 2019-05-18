package com.spring.internship.service.Impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.internship.model.Role;
import com.spring.internship.model.RoleName;
import com.spring.internship.repository.RoleRepository;
import com.spring.internship.service.IRoleService;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private RoleRepository repo;

	@Override
	public Role save(Role entity) {
		return repo.save(entity);
	}

	@Override
	public Role update(Role entity) {
		return repo.save(entity);
	}

	@Override
	public List<Role> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Role> getById(Long id) {
		return repo.findById(id);
	}

	@Override
	public void remove(Long id) {
		repo.deleteById(id);
		
	}
	
	@Override
	public Optional<Role> findByName(RoleName roleName) {
		return repo.findByName(roleName);
	}

}
