package com.spring.internship.service.Impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.internship.model.User;
import com.spring.internship.repository.UserRepository;
import com.spring.internship.service.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
	
	@Autowired
	UserRepository repo;

	@Override
	public User save(User entity) {
		return repo.save(entity);
	}

	@Override
	public User update(User entity) {
		return repo.save(entity);
	}

	@Override
	public List<User> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<User> getById(Long id) {
		return repo.findById(id);
	}

	@Override
	public void remove(Long id) {
		repo.deleteById(id);
		
	}

}
