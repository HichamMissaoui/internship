package com.spring.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.internship.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

}
