package com.pragma.laboratorio.customer.controller;

import java.util.List;

import javax.validation.Valid;

import com.pragma.laboratorio.customer.dto.IdTypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pragma.laboratorio.customer.entity.IdType;
import com.pragma.laboratorio.customer.services.IdTypeService;

@RestController
@RequestMapping("/id-type")
@CrossOrigin
public class IdTypeController {

	@Autowired
	private IdTypeService typeService;
	
	@GetMapping
	public List<IdTypeDto> list() {
		return  this.typeService.findAll();
	}
	
	@GetMapping("/{idType}")
	public ResponseEntity<IdTypeDto> listById(@PathVariable int idType) {
		return  this.typeService.findById(idType);
	}
	
	@PostMapping
	public ResponseEntity<IdTypeDto> save( @RequestBody IdType type,BindingResult result) {
		return typeService.save(type,result);
		
	}
	
	@DeleteMapping("/{idType}")
	public ResponseEntity<?> deleteById(@PathVariable int idType){
		
		if(typeService.deleteById(idType)) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
	@PutMapping("/{idType}")
	public ResponseEntity<?> update(@Valid @RequestBody IdType type, BindingResult result, @PathVariable int idType){
		return this.typeService.updateIdType(type, result, idType);
	}
	
}
