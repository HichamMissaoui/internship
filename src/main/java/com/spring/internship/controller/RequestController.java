package com.spring.internship.controller;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.spring.internship.model.Request;
import com.spring.internship.model.Skill;
import com.spring.internship.model.Technology;
import com.spring.internship.model.User;
import com.spring.internship.service.IRequestService;
import com.spring.internship.service.ISkillService;
import com.spring.internship.service.ITechnologyService;
import com.spring.internship.service.IUserService;

@RestController
public class RequestController {

	@Autowired
	private ITechnologyService technologyService;

	@Autowired
	private ISkillService skillService;

	@Autowired
	private IRequestService requestService;

	@Autowired
	private IUserService userService;

	@PostMapping("/requests")
	@PreAuthorize("hasRole('USER')")
	public Request makeRequest(@RequestBody ObjectNode objectNode) {
		Request request = new Request();
		
		// SET REQUEST DATESTART & DATEEND
		try {
			request.setDateStart(new SimpleDateFormat("yyyy-MM-dd").parse(objectNode.get("dateStart").asText()));
			request.setDateEnd(new SimpleDateFormat("yyyy-MM-dd").parse(objectNode.get("dateEnd").asText()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// SET REQUEST USER
		long userId = objectNode.get("userId").asLong();
		User user = userService.getById(userId).orElseThrow(() -> new EntityNotFoundException());
		request.setUser(user);
		
		// SET REQUEST SKILLS
		request.setSkills(getObjectNodeSkills(objectNode));
		
		// SET REQUEST TECHNOLOGIES
		request.setTechnologies(getObjectNodeTechnologies(objectNode));
	
		
		return requestService.save(request);
	}
	
	
	
	
	@GetMapping("/requests/{id}")
	@PreAuthorize("hasRole('USER')")
	public Request getRequest(@PathVariable(value = "id") Long id) {
		return requestService.getById(id).orElseThrow(() -> new EntityNotFoundException());
	}

	@GetMapping("/requests")
	@PreAuthorize("hasRole('USER')")
	public List<Request> getAllRequest() {
		return requestService.getAll();
	}
	
	
	@PutMapping("/requests/{id}")
	@PreAuthorize("hasRole('USER')")
	public Request updateRequest(@RequestBody ObjectNode objectNode, @PathVariable(value = "id") Long id) {
		Request request = requestService.getById(id).orElseThrow(() -> new EntityNotFoundException());
		
		
		// SET REQUEST DATESTART & DATEEND
		try {
			request.setDateStart(new SimpleDateFormat("yyyy-MM-dd").parse(objectNode.get("dateStart").asText()));
			request.setDateEnd(new SimpleDateFormat("yyyy-MM-dd").parse(objectNode.get("dateEnd").asText()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// SET REQUEST SKILLS
		request.getSkills().clear();
		request.setSkills(getObjectNodeSkills(objectNode));
		
		// SET REQUEST TECHNOLOGIES
		request.getTechnologies().clear();
		request.setTechnologies(getObjectNodeTechnologies(objectNode));

		
		return requestService.update(request);
	}
	
	@DeleteMapping("/requests/{id}")
	@PreAuthorize("hasRole('USER')")
	public void deleteRequest(@PathVariable(value = "id") Long id) {
		requestService.remove(id);
	}
	
	public Set<Skill> getObjectNodeSkills(ObjectNode objectNode){
		Set<Skill> skills = new HashSet<Skill>();
		ArrayNode skillsArrayNode = objectNode.withArray("skills");
		for (JsonNode jsonNode : skillsArrayNode) {
			Skill skill = new Skill();
			skill = skillService.getById(jsonNode.asLong()).orElseThrow(() -> new EntityNotFoundException());
			skills.add(skill);
		}
		return skills;
	}
	
	public Set<Technology> getObjectNodeTechnologies(ObjectNode objectNode){
		Set<Technology> technologies = new HashSet<Technology>();
		ArrayNode technoArrayNode = objectNode.withArray("technologies");
		for (JsonNode jsonNode : technoArrayNode) {
			Technology technology = new Technology();
			technology = technologyService.getById(jsonNode.asLong()).orElseThrow(() -> new EntityNotFoundException());
			technologies.add(technology);
		}
		return technologies;
	}

}
