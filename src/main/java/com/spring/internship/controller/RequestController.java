package com.spring.internship.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.internship.model.Skill;
import com.spring.internship.model.Technology;
import com.spring.internship.service.ISkillService;
import com.spring.internship.service.ITechnologyService;

@RestController
public class RequestController {
	
	@Autowired
    private ITechnologyService technoService;
	
	@Autowired
	private ISkillService skillService;
		
	
	@GetMapping("/technologies")
    @PreAuthorize("hasRole('USER')")
    public List<Technology> getAllTechnologies(){
    	return technoService.getAll();
    }
	
	@GetMapping("/skills")
    @PreAuthorize("hasRole('USER')")
    public List<Skill> getAllSkills(){
    	return skillService.getAll();
    }

}
