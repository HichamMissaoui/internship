package com.spring.internship.util;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.spring.internship.model.Skill;
import com.spring.internship.model.Technology;
import com.spring.internship.service.ISkillService;
import com.spring.internship.service.ITechnologyService;

@Component
public class JsonHelper {
	
	@Autowired
	private ITechnologyService technologyService;

	@Autowired
	private ISkillService skillService;

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
