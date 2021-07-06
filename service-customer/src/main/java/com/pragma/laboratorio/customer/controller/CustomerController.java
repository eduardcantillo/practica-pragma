package com.pragma.laboratorio.customer.controller;

import java.util.List;

import javax.validation.Valid;

import com.pragma.laboratorio.customer.dto.CustomerDto;
import com.pragma.laboratorio.customer.dto.FotoDto;
import com.pragma.laboratorio.customer.entity.IdType;
import com.pragma.laboratorio.customer.foto.rest.FotoRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.pragma.laboratorio.customer.entity.Customer;
import com.pragma.laboratorio.customer.services.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private FotoRest fotos;

	@GetMapping
	public List<CustomerDto> findAll(){
		return this.customerService.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CustomerDto> findById(@PathVariable String id){
		return this.customerService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<CustomerDto> DeleteById(@PathVariable String id){
		return (this.customerService.deleteById(id))? ResponseEntity.ok().build():ResponseEntity.notFound().build();
	}
	
	@GetMapping("/age/{age}")
	public ResponseEntity<List<CustomerDto>> findById(@PathVariable int age){
		return this.customerService.findByAge(age);
	}
	
	@PostMapping
	public ResponseEntity<CustomerDto> save(@RequestBody CustomerDto customerDto){
		return this.customerService.save(customerDto);
	}
	
	@PutMapping("/{identificacion}")
	public ResponseEntity<CustomerDto> update( @RequestBody CustomerDto customer,BindingResult result,@PathVariable String identificacion){
		
		return this.customerService.update(customer,identificacion);
	}
	@PostMapping("/id-type/{id}")
	public ResponseEntity<CustomerDto> findByType(@RequestBody IdType type,@PathVariable String id){

		return this.customerService.findByIdTypeAndIdentificacion(type,id);
	}
	@PostMapping("/id-type")
	public ResponseEntity<List<CustomerDto>> findByType(@RequestBody IdType type){

		return this.customerService.findByIdType(type);
	}

	@GetMapping("/foto")
	public ResponseEntity<List<FotoDto>> listarFoto(){
		return this.fotos.listAll();
	}
	
}
