package com.meritamerica.bank2.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.bank2.models.CDOffering;



@RestController
public class CDOfferingController {
	
List<CDOffering> cdOfferings = new ArrayList<CDOffering>();
	
	@PostMapping(value ="/CDOfferings")
	@ResponseStatus(HttpStatus.CREATED)
	public CDOffering addCdOffering(@RequestBody CDOffering cdOffering) {
		cdOfferings.add(cdOffering);
		return cdOffering;
		
	}
	
	@GetMapping(value ="/CDOfferings")
	@ResponseStatus(HttpStatus.OK)
	public List<CDOffering> getCdOffering() {
		return cdOfferings;
		
	}

	

}
