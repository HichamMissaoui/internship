package com.spring.internship.service.Impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.internship.model.Request;
import com.spring.internship.repository.RequestRepository;
import com.spring.internship.service.IRequestService;

@Service
@Transactional
public class RequestService implements IRequestService {
	
	@Autowired
	private RequestRepository repo;

	@Override
	public Request save(Request entity) {
		return repo.save(entity);
	}

	@Override
	public Request update(Request entity) {
		return repo.save(entity);
	}

	@Override
	public List<Request> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Request> getById(Long id) {
		return repo.findById(id);
	}

	@Override
	public void remove(Long id) {
		repo.deleteById(id);
		
	}

}
