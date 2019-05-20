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
import com.spring.internship.model.Technology;
import com.spring.internship.service.ITechnologyService;
import com.spring.internship.util.JsonHelper;

@RestController
@RequestMapping("/technologies")
public class TechnologyController {

	@Autowired
	private ITechnologyService technoService;
	
	@Autowired
	private JsonHelper jsonHelper;
	
	@GetMapping("/")
	@PreAuthorize("hasRole('USER')")
	public List<Technology> getAllTechnologys(){
		return technoService.getAll();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Technology getTechnology(@PathVariable(value= "id") Long id) {
		return technoService.getById(id).orElseThrow(() -> new EntityNotFoundException());
	}
	
	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	public Technology createTechnology(@RequestBody ObjectNode objectNode) {
		Technology technology = new Technology();
		technology.setName(objectNode.get("name").asText());
		
		// SET TECHNOLOGY SKILLS
		technology.setSkills(jsonHelper.getObjectNodeSkills(objectNode));
		
		return technoService.save(technology);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public Technology updateTechnology(@RequestBody ObjectNode objectNode,@PathVariable(value = "id") Long id) {
		Technology technology = technoService.getById(id).orElseThrow(() -> new EntityNotFoundException());
		technology.setName(objectNode.get("name").asText());
		
		// SET TECHNOLOGY SKILLS
		technology.getSkills().clear();
		technology.setSkills(jsonHelper.getObjectNodeSkills(objectNode));
				
		return technoService.update(technology);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public void removeTechnology(@PathVariable(value="id") Long id) {
		technoService.remove(id);
	}
	

}
