package com.pragma.laboratorio.customer.services;

import java.util.List;

import com.pragma.laboratorio.customer.dto.CustomerDto;
import com.pragma.laboratorio.customer.entity.IdType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {

	public ResponseEntity<List<CustomerDto>> findAll();
	public ResponseEntity<CustomerDto> findById(String identificacion);
	public ResponseEntity<CustomerDto>  save(CustomerDto customerDto);
	public boolean deleteById(String identificion);
	public ResponseEntity<CustomerDto> update(CustomerDto customer,String cedulaCustomer);
	public ResponseEntity<List<CustomerDto>> findByAge(int age);
	public ResponseEntity<List<CustomerDto>> findByIdType(IdType idType);
	public ResponseEntity<CustomerDto> findByIdTypeAndIdentificacion(IdType idType,String id);
}
