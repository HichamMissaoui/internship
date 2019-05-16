package com.spring.internship.service;

import java.util.List;
import java.util.Optional;


public interface IGenericService<E> {

	public E save(E entity);

	public E update(E entity);

	public List<E> getAll();

	public Optional<E> getById(Long id);

	public void remove(Long id);
}
