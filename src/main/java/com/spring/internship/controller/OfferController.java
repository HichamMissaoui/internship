package com.spring.internship.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.spring.internship.model.Offer;
import com.spring.internship.model.User;
import com.spring.internship.service.IOfferService;
import com.spring.internship.service.IUserService;
import com.spring.internship.util.JsonHelper;

@RestController
@RequestMapping("/offers")
public class OfferController {
	@Autowired
	private IOfferService offerService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private JsonHelper jsonHelper;

	@PostMapping("/")
	@PreAuthorize("hasRole('USER')")
	public Offer makeOffer(@RequestBody ObjectNode objectNode) {
		Offer offer = new Offer();
		
		// SET OFFER DATESTART & DATEEND
		try {
			offer.setDateStart(new SimpleDateFormat("yyyy-MM-dd").parse(objectNode.get("dateStart").asText()));
			offer.setDateEnd(new SimpleDateFormat("yyyy-MM-dd").parse(objectNode.get("dateEnd").asText()));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// SET OFFER USER
		long userId = objectNode.get("userId").asLong();
		User user = userService.getById(userId).orElseThrow(() -> new EntityNotFoundException());
		offer.setUser(user);
		
		// SET OFFER SKILLS
		offer.setSkills(jsonHelper.getObjectNodeSkills(objectNode));
		
		// SET OFFER TECHNOLOGIES
		offer.setTechnologies(jsonHelper.getObjectNodeTechnologies(objectNode));
	
		
		return offerService.save(offer);
	}
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public Offer getOffer(@PathVariable(value = "id") Long id) {
		return offerService.getById(id).orElseThrow(() -> new EntityNotFoundException());
	}

	@GetMapping("/")
	@PreAuthorize("hasRole('USER')")
	public List<Offer> getAllOffer() {
		return offerService.getAll();
	}
	
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public Offer updateOffer(@RequestBody ObjectNode objectNode, @PathVariable(value = "id") Long id) {
		Offer offer = offerService.getById(id).orElseThrow(() -> new EntityNotFoundException());
		
		
		// SET OFFER DATESTART & DATEEND
		try {
			offer.setDateStart(new SimpleDateFormat("yyyy-MM-dd").parse(objectNode.get("dateStart").asText()));
			offer.setDateEnd(new SimpleDateFormat("yyyy-MM-dd").parse(objectNode.get("dateEnd").asText()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		// SET OFFER SKILLS
		offer.getSkills().clear();
		offer.setSkills(jsonHelper.getObjectNodeSkills(objectNode));
		
		// SET OFFER TECHNOLOGIES
		offer.getTechnologies().clear();
		offer.setTechnologies(jsonHelper.getObjectNodeTechnologies(objectNode));

		
		return offerService.update(offer);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public void deleteOffer(@PathVariable(value = "id") Long id) {
		offerService.remove(id);
	}
	

}
