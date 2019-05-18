package com.spring.internship.service.Impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.internship.model.Offer;
import com.spring.internship.repository.OfferRepository;
import com.spring.internship.service.IOfferService;

@Service
@Transactional
public class OfferServiceImpl implements IOfferService {
	
	@Autowired
	OfferRepository repo;

	@Override
	public Offer save(Offer entity) {
		return repo.save(entity);
	}

	@Override
	public Offer update(Offer entity) {
		return repo.save(entity);
	}

	@Override
	public List<Offer> getAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Offer> getById(Long id) {
		return repo.findById(id);
	}

	@Override
	public void remove(Long id) {
		repo.deleteById(id);
		
	}

}
