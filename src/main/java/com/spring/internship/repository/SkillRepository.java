package com.spring.internship.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.internship.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill,Long> {

}
