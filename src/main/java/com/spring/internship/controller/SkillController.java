package com.spring.internship.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.spring.internship.model.Skill;
import com.spring.internship.service.ISkillService;

@RestController
@RequestMapping("skills")
public class SkillController {
	@Autowired
	private ISkillService skillService;
	
	@GetMapping("/")
	@PreAuthorize("hasRole('USER')")
	public List<Skill> getAllSkills(){
		return skillService.getAll();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Skill getSkill(@PathVariable(value= "id") Long id) {
		return skillService.getById(id).orElseThrow(() -> new EntityNotFoundException());
	}
	
	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public Skill createSkill(@RequestBody ObjectNode objectNode) {
		Skill skill = new Skill();
		skill.setName(objectNode.get("name").asText());
		return skillService.save(skill);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Skill updateSkill(@RequestBody ObjectNode objectNode,@PathVariable(value = "id") Long id) {
		Skill skill = skillService.getById(id).orElseThrow(() -> new EntityNotFoundException());
		skill.setName(objectNode.get("name").asText());
		return skillService.update(skill);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void removeSkill(@PathVariable(value="id") Long id) {
		skillService.remove(id);
	}
	

}
